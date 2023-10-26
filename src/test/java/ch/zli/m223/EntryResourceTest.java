package ch.zli.m223;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDateTime;

@QuarkusTest
public class EntryResourceTest {

    @Inject
    private EntryService entryService;

    @BeforeEach
    public void initDB() {

        Entry entry1 = new Entry();
        entry1.setCheckIn(LocalDateTime.of(2022, 03, 10, 12, 15, 50));
        entry1.setCheckOut(entry1.getCheckIn().plusHours(1));

        entryService.createEntry(entry1);
    }

    @Test
    public void testIndexEndpoint() {
        given()
                .when().get("/entries")
                .then()
                .statusCode(200)
                .body(is("[{\"id\":1,\"checkIn\":\"2022-03-10T12:15:50\",\"checkOut\":\"2022-03-10T13:15:50\"}]"));
    }

    @Test
    public void testDelete() {
        given()
                .when().delete("/entries/1")
                .then()
                .statusCode(204)
                .body(is(""));
    }

    @Test
    public void testUpdateEndpoint() {
        Entry entryToUpdate = new Entry();
        entryToUpdate.setCheckIn(LocalDateTime.of(2022, 03, 10, 12, 15, 50));
        entryToUpdate.setCheckOut(entryToUpdate.getCheckIn().plusHours(2));
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(entryToUpdate)
                .when().put("/entries/1")
                .then()
                .statusCode(200)
                .body(is("{\"id\":1,\"checkIn\":\"2022-03-10T12:15:50\",\"checkOut\":\"2022-03-10T14:15:50\"}"));
    }

    @Test
    public void testCreateEndpoint() {
        Entry entryToUpdate = new Entry();
        entryToUpdate.setCheckIn(LocalDateTime.of(2022, 02, 10, 12, 15, 50));
        entryToUpdate.setCheckOut(entryToUpdate.getCheckIn().plusHours(2));
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(entryToUpdate)
                .when().post("/entries/")
                .then()
                .statusCode(200)
                .body(is("{\"id\":2,\"checkIn\":\"2022-02-10T12:15:50\",\"checkOut\":\"2022-02-10T14:15:50\"}"));
    }

}