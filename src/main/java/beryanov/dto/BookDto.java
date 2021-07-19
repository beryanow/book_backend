package beryanov.dto;

import com.sun.istack.NotNull;
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
    private List<QuoteDto> quotes;
}
