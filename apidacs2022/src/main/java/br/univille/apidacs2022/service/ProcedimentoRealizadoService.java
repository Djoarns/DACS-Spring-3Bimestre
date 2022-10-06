package br.univille.apidacs2022.service;

import java.util.List;

import br.univille.coredacs2022.entity.Procedimento;
import br.univille.coredacs2022.entity.ProcedimentoRealizado;

public interface ProcedimentoRealizadoService {
    public List<ProcedimentoRealizado> getAll();
    public ProcedimentoRealizado save(ProcedimentoRealizado procedimentoRealizado);
    public ProcedimentoRealizado findById(long id);
    public ProcedimentoRealizado delete(long id);
    public List<ProcedimentoRealizado> getByProcedimento(Procedimento procedimento);
}
