package src;
import java.util.List;

public interface IMangaRepository {
    void save();
    boolean create(Manga manga);
    Manga readByISBN(String isbn);
    List<Manga> readByTitle(String title);
    boolean update(Manga updatedManga);
    boolean delete(String isbn);
    void listAll();
}
