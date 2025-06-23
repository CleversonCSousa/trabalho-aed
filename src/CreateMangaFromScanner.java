package src;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CreateMangaFromScanner {

    private Scanner scanner;

    public CreateMangaFromScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Manga execute() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Autor(es) (separados por vírgula): ");
        List<String> authors = Arrays.asList(scanner.nextLine().split(",\\s*"));

        System.out.print("Ano de Início (-1 para desconhecido): ");
        int startYear = Integer.parseInt(scanner.nextLine());

        System.out.print("Ano de Fim (-1 para desconhecido): ");
        int endYear = Integer.parseInt(scanner.nextLine());

        System.out.print("Gêneros (separados por vírgula): ");
        List<String> genres = Arrays.asList(scanner.nextLine().split(",\\s*"));

        System.out.print("Revista: ");
        String magazine = scanner.nextLine();

        System.out.print("Editora: ");
        String publisher = scanner.nextLine();

        System.out.print("Ano da Edição: ");
        int editionYear = Integer.parseInt(scanner.nextLine());

        System.out.print("Total de Volumes: ");
        int totalVolumes = Integer.parseInt(scanner.nextLine());

        System.out.print("Volumes Adquiridos (números separados por vírgula, ex: 1,3,5): ");
        String acquiredVolumesInput = scanner.nextLine();
        List<Integer> acquiredVolumeNumbers = new ArrayList<>();
        if (!acquiredVolumesInput.trim().isEmpty()) {
            for (String s : acquiredVolumesInput.split(",\\s*")) {
                acquiredVolumeNumbers.add(Integer.parseInt(s.trim()));
            }
        }
        int acquiredVolumesCount = acquiredVolumeNumbers.size();

        return new Manga(
            isbn, title, authors, startYear, endYear, genres,
            magazine, publisher, editionYear, totalVolumes,
            acquiredVolumesCount, acquiredVolumeNumbers
        );
    }
}