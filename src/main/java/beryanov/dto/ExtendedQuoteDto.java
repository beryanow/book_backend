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
public class ExtendedQuoteDto {
    private String id;
    @NotBlank
    private String content;
    private String bookName;
    private String bookAuthor;
    private Date createdDate;
    private Date lastUpdatedDate;
}