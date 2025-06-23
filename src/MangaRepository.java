package src;
import java.io.*;
import java.util.*;

public class MangaRepository implements IMangaRepository {
    private Map<String, Manga> mangas = new HashMap<>();
    private Map<String, List<String>> indexTitleToIsbn = new HashMap<>();

    private final String mangasFileTxt = "./database/mangas.txt";
    private final String indexPrimaryFile = "./database/index_primary.txt";
    private final String indexSecondaryFile = "./database/index_secondary.txt";

    public MangaRepository() {
        loadFromTxt();
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(mangasFileTxt))) {
            for (Manga manga : mangas.values()) {
                bw.write(formatMangaAsTxtLine(manga));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar mangás: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(indexPrimaryFile))) {
            for (String isbn : mangas.keySet()) {
                bw.write(isbn);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar índice primário: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(indexSecondaryFile))) {
            for (Map.Entry<String, List<String>> entry : indexTitleToIsbn.entrySet()) {
                bw.write(entry.getKey() + ";" + String.join(",", entry.getValue()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar índice secundário: " + e.getMessage());
        }
    }

    private String formatMangaAsTxtLine(Manga m) {
        return String.join("; ",
            m.getIsbn(),
            m.getTitle(),
            String.join(", ", m.getAuthors()),
            m.getStartYear() == -1 ? "-" : String.valueOf(m.getStartYear()),
            m.getEndYear() == -1 ? "-" : String.valueOf(m.getEndYear()),
            String.join(", ", m.getGenres()),
            m.getMagazine(),
            m.getPublisher(),
            String.valueOf(m.getEditionYear()),
            String.valueOf(m.getTotalVolumes()),
            String.valueOf(m.getAcquiredVolumesCount()),
            m.getAcquiredVolumeNumbers().toString()
        );
    }

    private int parseYear(String s) {
        return s.equals("-") ? -1 : Integer.parseInt(s);
    }

    private void loadFromTxt() {
        mangas.clear();
        indexTitleToIsbn.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(mangasFileTxt))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("; ", 12);
                if (parts.length != 12) {
                    System.err.println("Linha " + lineNumber + " com número errado de campos.");
                    continue;
                }

                try {
                    String isbn = parts[0];
                    String title = parts[1];
                    List<String> authors = Arrays.asList(parts[2].split(",\\s*"));
                    int startYear = parseYear(parts[3]);
                    int endYear = parseYear(parts[4]);
                    List<String> genres = Arrays.asList(parts[5].split(",\\s*"));
                    String magazine = parts[6];
                    String publisher = parts[7];
                    int editionYear = Integer.parseInt(parts[8]);
                    int totalVolumes = Integer.parseInt(parts[9]);
                    int acquiredVolumesCount = Integer.parseInt(parts[10]);

                    String volsRaw = parts[11].trim();
                    volsRaw = volsRaw.substring(1, volsRaw.length() - 1);
                    List<Integer> acquiredVolumes = new ArrayList<>();
                    if (!volsRaw.isEmpty()) {
                        for (String v : volsRaw.split(",\\s*")) {
                            acquiredVolumes.add(Integer.parseInt(v));
                        }
                    }

                    Manga manga = new Manga(isbn, title, authors, startYear, endYear, genres,
                                            magazine, publisher, editionYear, totalVolumes,
                                            acquiredVolumesCount, acquiredVolumes);

                    mangas.put(isbn, manga);
                    indexTitleToIsbn.computeIfAbsent(title, key -> new ArrayList<>()).add(isbn);

                } catch (Exception e) {
                    System.err.println("Erro ao processar linha " + lineNumber + ": " + e.getMessage());
                }
            }
            System.out.println("Carregados " + mangas.size() + " mangás do arquivo.");
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo '" + mangasFileTxt + "' não encontrado.");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo '" + mangasFileTxt + "'");
        }
    }

    public boolean create(Manga manga) {
        if (mangas.containsKey(manga.getIsbn())) {
            System.out.println("Mangá com ISBN já existe.");
            return false;
        }
        mangas.put(manga.getIsbn(), manga);
        indexTitleToIsbn.computeIfAbsent(manga.getTitle(), key -> new ArrayList<>()).add(manga.getIsbn());
        save();
        return true;
    }

    public Manga readByISBN(String isbn) {
        return mangas.get(isbn);
    }

    public List<Manga> readByTitle(String title) {
        List<Manga> result = new ArrayList<>();
        List<String> isbns = indexTitleToIsbn.get(title);
        if (isbns != null) {
            for (String isbn : isbns) {
                Manga m = mangas.get(isbn);
                if (m != null) result.add(m);
            }
        }
        return result;
    }

    public boolean update(Manga updatedManga) {
        if (!mangas.containsKey(updatedManga.getIsbn())) {
            System.out.println("Mangá não encontrado.");
            return false;
        }

        Manga oldManga = mangas.get(updatedManga.getIsbn());

        if (!oldManga.getTitle().equals(updatedManga.getTitle())) {
            List<String> oldList = indexTitleToIsbn.get(oldManga.getTitle());
            if (oldList != null) {
                oldList.remove(updatedManga.getIsbn());
                if (oldList.isEmpty()) {
                    indexTitleToIsbn.remove(oldManga.getTitle());
                }
            }
            indexTitleToIsbn.computeIfAbsent(updatedManga.getTitle(), key -> new ArrayList<>()).add(updatedManga.getIsbn());
        }

        mangas.put(updatedManga.getIsbn(), updatedManga);
        save();
        return true;
    }

    public boolean delete(String isbn, Scanner scanner) {
        if (!mangas.containsKey(isbn)) {
            System.out.println("Mangá não encontrado.");
            return false;
        }

        System.out.println("Confirma exclusão do mangá de ISBN " + isbn + "? (S/N)");
        String resp = scanner.nextLine().trim().toUpperCase();
        if (!resp.equals("S")) {
            System.out.println("Exclusão cancelada.");
            return false;
        }

        Manga manga = mangas.remove(isbn);

        List<String> list = indexTitleToIsbn.get(manga.getTitle());
        if (list != null) {
            list.remove(isbn);
            if (list.isEmpty()) {
                indexTitleToIsbn.remove(manga.getTitle());
            }
        }

        save();
        return true;
    }

    public void listAll() {
        if (mangas.isEmpty()) {
            System.out.println("Nenhum mangá cadastrado.");
            return;
        }
        for (Manga m : mangas.values()) {
            System.out.println(m);
            System.out.println("----------");
        }
    }
}