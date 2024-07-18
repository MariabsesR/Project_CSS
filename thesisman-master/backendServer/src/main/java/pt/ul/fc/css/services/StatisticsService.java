package pt.ul.fc.css.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.repositories.ThesisDefenceRepository;

@Service
public class StatisticsService {

    @Autowired
    private ThesisDefenceRepository thesisDefenceRepository;

    public double calculateSuccessRate() {
        int duration = 90; // Only consider defenses with a 90-minute duration
        long totalDefences = thesisDefenceRepository.countByDuration(duration);
        long passedDefences = thesisDefenceRepository.countByDurationAndGradeGreaterThanEqual(duration, 10.0);

        if (totalDefences == 0) {
            return 0;
        }

        return (double) passedDefences / totalDefences * 100;
    }
}
