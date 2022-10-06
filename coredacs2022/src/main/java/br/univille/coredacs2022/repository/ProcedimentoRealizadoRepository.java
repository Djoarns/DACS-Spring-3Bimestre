package br.univille.coredacs2022.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.univille.coredacs2022.entity.Procedimento;
import br.univille.coredacs2022.entity.ProcedimentoRealizado;

@Repository
public interface ProcedimentoRealizadoRepository extends JpaRepository<ProcedimentoRealizado, Long> {
    List<ProcedimentoRealizado> findByProcedimento(@Param("procedimento") Procedimento procedimento);
}
