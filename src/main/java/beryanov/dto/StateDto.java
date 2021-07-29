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
public class StateDto {
    private String id;
    private boolean flag;
    @NotBlank
    private String option;
    private String rating;
    @NotBlank
    private String bookId;
    private Date createdDate;
}
