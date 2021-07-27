package beryanov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String author;
    @NotBlank
    private String fileName;
    private StateDto read;
    private StateDto reading;
    private StateDto toRead;
    private StateDto favourite;
    private List<QuoteDto> quotes;
    private CritiqueDto critique;
}
