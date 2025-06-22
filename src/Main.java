package src;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        IMangaRepository mangaRepository = new MangaRepository();
        
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Escolha opção:");
            System.out.println("1 - Criar mangá");
            System.out.println("2 - Buscar por ISBN");
            System.out.println("3 - Buscar por título");
            System.out.println("4 - Listar todos");
            System.out.println("5 - Atualizar mangá");
            System.out.println("6 - Apagar mangá");
            System.out.println("0 - Sair");
            String op = sc.nextLine();

            try {
                switch (op) {
                    case "1":
                        CreateMangaFromScanner cmfs = new CreateMangaFromScanner();
                        Manga mangaCreated = cmfs.execute();
                        mangaRepository.create(mangaCreated);
                        break;
                    case "2":
                        System.out.print("Digite ISBN: ");
                        String isbn = sc.nextLine();
                        Manga manga = mangaRepository.readByISBN(isbn);
                        if (manga != null) System.out.println(manga);
                        else System.out.println("Mangá não encontrado.");
                        break;
                    case "3":
                        System.out.print("Digite título: ");
                        String title = sc.nextLine();
                        List<Manga> list = mangaRepository.readByTitle(title);
                        if (list.isEmpty()) System.out.println("Nenhum mangá com esse título.");
                        else list.forEach(System.out::println);
                        break;
                    case "4":
                        mangaRepository.listAll();
                        break;
                    case "5":
                        System.out.print("Digite ISBN para atualizar: ");
                        String isbnUpdate = sc.nextLine();
                        Manga oldManga = mangaRepository.readByISBN(isbnUpdate);
                        if (oldManga == null) {
                            System.out.println("Mangá não encontrado.");
                        } else {
                            CreateMangaFromScanner createMangaFromScanner = new CreateMangaFromScanner();
                            Manga newManga = createMangaFromScanner.execute();
                            mangaRepository.update(newManga);
                        }
                        break;
                    case "6":
                        System.out.print("Digite ISBN para apagar: ");
                        String isbnDel = sc.nextLine();
                        mangaRepository.delete(isbnDel);
                        break;
                    case "0":
                        System.out.println("Saindo...");
                        sc.close();
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

}
