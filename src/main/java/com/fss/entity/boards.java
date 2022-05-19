package com.fss.entity;

import com.fss.request.UpdateBoard;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;
import lombok.Data;
import org.bson.types.ObjectId;


@Data
public class boards extends ReactivePanacheMongoEntity {

    public String title;
    public String content;

    public boards() {
    }

    public boards(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Uni<boards> updateBoard(String id, UpdateBoard updateBoard) {
        Uni<boards> boardUni = boards.findById(new ObjectId(id));
        return boardUni
                .onItem().transform(board -> {
                    board.content = updateBoard.getContent();
                    board.title = updateBoard.getTitle();
                    return board;
                }).call(board -> board.persistOrUpdate());
    }

}