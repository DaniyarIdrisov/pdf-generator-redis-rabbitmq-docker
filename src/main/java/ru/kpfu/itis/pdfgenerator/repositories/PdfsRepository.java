package ru.kpfu.itis.pdfgenerator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.pdfgenerator.models.Pdf;

@Repository
public interface PdfsRepository extends JpaRepository<Pdf, Long> {
}
