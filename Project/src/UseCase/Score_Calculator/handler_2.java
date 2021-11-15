package UseCase.Score_Calculator;

import Entities.Listing.JobListing;
import Entities.User.User;
import Entities.User.Experience;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;

public class handler_2 extends handler_ext_work{

    public handler_2(User user_input, JobListing job_input) {
        super(user_input, job_input);
    }

    @Override
    public void score_calculate() {
        double score = 0.0;
        ArrayList<Experience> user_experiences = (ArrayList<Experience>) this.user.getData(User.REL_WORK_EXP);
        if(user_experiences != null){
            for(Experience experience : user_experiences) {
                LocalDate start_date = (LocalDate) experience.getData(Experience.START_TIME);
                LocalDate end_date = (LocalDate) experience.getData(Experience.END_TIME);
                String title = (String) experience.getData(Experience.EXPERIENCE_TITLE);
                ArrayList<String> description = (ArrayList<String>) experience.getData(Experience.EXPERIENCE_DESCRPTION);
                double experience_score = score_calc_time(start_date, end_date);
                score += experience_score * description.size();
            }
        }

        this.score += score;

    }

}



