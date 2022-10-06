package br.univille.dacs2022.service;

import java.util.List;

import br.univille.dacs2022.dto.CidadeDTO;

public interface CidadeService {
    public List<CidadeDTO> getAll();
    public CidadeDTO findById(long id);
    public CidadeDTO save(CidadeDTO cidade);
    public CidadeDTO delete(long id);
}
