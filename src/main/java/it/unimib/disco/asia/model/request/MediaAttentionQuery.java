package it.unimib.disco.asia.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaAttentionQuery {

    @JsonProperty("$from")
    private String from;

    @JsonProperty("EventFeatureId")
    private String eventFeatureId;

    @JsonIgnore
    private String category;

}
