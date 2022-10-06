package br.univille.dacs2022.service.impl;

import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.univille.dacs2022.dto.PlanoDeSaudeDTO;
import br.univille.coredacs2022.entity.PlanoDeSaude;
import br.univille.dacs2022.mapper.PlanoDeSaudeMapper;
import br.univille.coredacs2022.repository.PlanoDeSaudeRepository;
import br.univille.dacs2022.service.PlanoDeSaudeService;

@Service
public class PlanoDeSaudeImpl implements PlanoDeSaudeService {
    @Autowired
    private PlanoDeSaudeRepository repository;
    private PlanoDeSaudeMapper mapper = Mappers.getMapper(PlanoDeSaudeMapper.class);;

    @Override
    public List<PlanoDeSaudeDTO> getAll() {
        List<PlanoDeSaude> listaPlanos = repository.findAll();
        return mapper.mapListPlanoDeSaude(listaPlanos);
    }

    @Override
    public PlanoDeSaudeDTO getById(long id) {
        Optional<PlanoDeSaude> planoEntity = repository.findById(id);

        if (planoEntity.isPresent()) {
            return mapper.mapPlanoDeSaude(planoEntity.get());
        }

        return new PlanoDeSaudeDTO();
    }

    @Override
    public PlanoDeSaudeDTO save(PlanoDeSaudeDTO plano) {
        PlanoDeSaude planoEntity = mapper.mapPlanoDeSaudeDTO(plano);
        planoEntity = repository.save(planoEntity);

        return mapper.mapPlanoDeSaude(planoEntity);
    }

    @Override
    public PlanoDeSaudeDTO delete(long id) {
        Optional<PlanoDeSaude> planoEntity = repository.findById(id);

        if (planoEntity.isPresent()) {
            PlanoDeSaude plano = planoEntity.get();
            repository.delete(plano);
            return mapper.mapPlanoDeSaude(plano);
        }

        return new PlanoDeSaudeDTO();
    }

}
