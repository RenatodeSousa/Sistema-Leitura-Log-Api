package com.log.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "log", schema = "log")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Log implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "log_id_seq")
    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "date")
    private LocalDateTime date;

    @JsonProperty("ip")
    @Column(name = "ip")
    private String ip;

    @JsonProperty("request")
    @Column(name = "request")
    private String request;

    @JsonProperty("status")
    @Column(name = "status")
    private Long status;

    @JsonProperty("userAgent")
    @Column(name = "userAgent")
    private String userAgent;
}
