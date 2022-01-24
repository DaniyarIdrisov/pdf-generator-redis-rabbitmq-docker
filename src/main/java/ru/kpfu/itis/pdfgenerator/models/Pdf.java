package ru.kpfu.itis.pdfgenerator.models;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pdf")
public class Pdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "pdf_bytes", nullable = false)
    private byte[] pdfBytes;

    @Column(name = "filename", nullable = false)
    private String filename;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
