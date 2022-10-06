package br.univille.dacs2022.service.impl;

import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.univille.dacs2022.dto.CidadeDTO;
import br.univille.coredacs2022.entity.Cidade;
import br.univille.dacs2022.mapper.CidadeMapper;
import br.univille.coredacs2022.repository.CidadeRepository;
import br.univille.dacs2022.service.CidadeService;

@Service
public class CidadeServiceImpl implements CidadeService {
    @Autowired
    private CidadeRepository repository;
    private CidadeMapper mapper = Mappers.getMapper(CidadeMapper.class);

    @Override
    public List<CidadeDTO> getAll() {
        List<Cidade> listaCidades = repository.findAll();

        return mapper.mapListCidade(listaCidades);
    }

    @Override
    public CidadeDTO findById(long id) {
        Optional<Cidade> cidade = repository.findById(id);

        if (cidade.isPresent()) {
            return mapper.mapCidade(cidade.get());
        }

        return new CidadeDTO();
    }

    @Override
    public CidadeDTO save(CidadeDTO cidade) {
        Cidade cidadeEntity = mapper.mapCidadeDTO(cidade);
        cidadeEntity = repository.save(cidadeEntity);

        return mapper.mapCidade(cidadeEntity);
    }

    @Override
    public CidadeDTO delete(long id) {
        Optional<Cidade> cidade = repository.findById(id);

        if (cidade.isPresent()) {
            repository.delete(cidade.get());
            return mapper.mapCidade(cidade.get());
        }

        return new CidadeDTO();
    }

}
