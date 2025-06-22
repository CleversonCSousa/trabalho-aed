package src;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CreateMangaFromScanner {

    public Manga execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Criar Novo Mangá ---");

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Autores (separados por vírgula): ");
        List<String> authors = Arrays.asList(scanner.nextLine().split(",\\s*"));

        System.out.print("Ano de Início (digite '-' se não souber): ");
        int startYear = parseYearInput(scanner.nextLine());

        System.out.print("Ano de Fim (digite '-' se não souber): ");
        int endYear = parseYearInput(scanner.nextLine());

        System.out.print("Gêneros (separados por vírgula): ");
        List<String> genres = Arrays.asList(scanner.nextLine().split(",\\s*"));

        System.out.print("Revista: ");
        String magazine = scanner.nextLine();

        System.out.print("Editora: ");
        String publisher = scanner.nextLine();

        System.out.print("Ano da Edição: ");
        int editionYear = readIntInput(scanner);

        System.out.print("Total de Volumes: ");
        int totalVolumes = readIntInput(scanner);

        System.out.print("Volumes Adquiridos (números separados por vírgula, ex: 1,2,5): ");
        List<Integer> acquiredVolumeNumbers = parseAcquiredVolumes(scanner.nextLine());
        int acquiredVolumesCount = acquiredVolumeNumbers.size();

        return new Manga(isbn, title, authors, startYear, endYear, genres,
                         magazine, publisher, editionYear, totalVolumes,
                         acquiredVolumesCount, acquiredVolumeNumbers);
    }

    private static int parseYearInput(String input) {
        if (input.equals("-")) {
            return -1;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Formato de ano inválido. Usando -1.");
            return -1;
        }
    }

    private static int readIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            scanner.next();
            System.out.print("Digite novamente: ");
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static List<Integer> parseAcquiredVolumes(String input) {
        List<Integer> volumes = new ArrayList<>();
        if (input.trim().isEmpty()) {
            return volumes;
        }
        String[] parts = input.split(",\\s*");
        for (String part : parts) {
            try {
                volumes.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Ignorando volume inválido: " + part);
            }
        }
        return volumes;
    }
}