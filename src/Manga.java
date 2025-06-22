package src;
import java.util.List;
import java.io.Serializable;

public class Manga implements Serializable {
    private String isbn;
    private String title;
    private List<String> authors;
    private int startYear;
    private int endYear;
    private List<String> genres;
    private String magazine;
    private String publisher;
    private int editionYear;
    private int totalVolumes;
    private int acquiredVolumesCount;
    private List<Integer> acquiredVolumeNumbers;

    public Manga(
        String isbn,
        String title,
        List<String> authors,
        int startYear,
        int endYear,
        List<String> genres,
        String magazine,
        String publisher,
        int editionYear,
        int totalVolumes,
        int acquiredVolumesCount,
        List<Integer> acquiredVolumeNumbers
    ) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.startYear = startYear;
        this.endYear = endYear;
        this.genres = genres;
        this.magazine = magazine;
        this.publisher = publisher;
        this.editionYear = editionYear;
        this.totalVolumes = totalVolumes;
        this.acquiredVolumesCount = acquiredVolumesCount;
        this.acquiredVolumeNumbers = acquiredVolumeNumbers;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getMagazine() {
        return magazine;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getEditionYear() {
        return editionYear;
    }

    public int getTotalVolumes() {
        return totalVolumes;
    }

    public int getAcquiredVolumesCount() {
        return acquiredVolumesCount;
    }

    public List<Integer> getAcquiredVolumeNumbers() {
        return acquiredVolumeNumbers;
    }

    @Override
    public String toString() {
        return String.format(
            "📖 Título: %s\n" +
            "🔢 ISBN: %s\n" +
            "✍️ Autor(es): %s\n" +
            "📅 Início: %s | Fim: %s\n" +
            "🎭 Gêneros: %s\n" +
            "🗞️ Revista: %s\n" +
            "🏢 Editora: %s\n" +
            "📘 Ano da Edição: %d\n" +
            "📚 Total de Volumes: %d | Adquiridos: %d\n" +
            "📦 Volumes Adquiridos: %s",
            title,
            isbn,
            String.join(", ", authors),
            (startYear == -1 ? "Desconhecido" : startYear),
            (endYear == -1 ? "Desconhecido" : endYear),
            String.join(", ", genres),
            magazine,
            publisher,
            editionYear,
            totalVolumes,
            acquiredVolumesCount,
            acquiredVolumeNumbers.toString()
        );
    }

}