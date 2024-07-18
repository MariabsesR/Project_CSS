package pt.ul.fc.css.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.repositories.MasterRepository;
import pt.ul.fc.css.entities.Master;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterService {

    @Autowired
    private MasterRepository masterRepository;

    public List<MasterDTO> getAllMasters() {
        List<Master> masters = masterRepository.findAll();
        return masters.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private MasterDTO convertToDto(Master master) {
        MasterDTO dto = new MasterDTO();
        dto.setId(master.getId());
        dto.setName(master.getName());
        return dto;
    }
}
