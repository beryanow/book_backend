package beryanov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuoteDto {
    private String id;
    @NotBlank
    private String content;
    @NotBlank
    private String bookId;
    private Date createdDate;
    private Date lastUpdatedDate;
}
