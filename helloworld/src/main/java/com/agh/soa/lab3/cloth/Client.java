package com.agh.soa.lab3.cloth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Client {

    private BasicData basic = new BasicData();
    private MaleData male = new MaleData();
    private FemaleData female = new FemaleData();
    private List<String> preferredClothTypes = new ArrayList<>();
    private Answers answers = new Answers();

    @Getter
    @Setter
    public class BasicData {
        private String name;
        private String email;
        private Gender gender;
        private Integer age;
        private String education;
        private Integer height;
    }

    @Getter
    @Setter
    public class MaleData {
        private Integer chestCircumference;
        private Integer waistCircumference;
    }

    @Getter
    @Setter
    public class FemaleData {
        private Integer bustPerimeter;
        private Integer waistCircumference;
        private String cupSize;
    }

    @Getter
    @Setter
    public class Answers {
        private String first;
        private String second;
        private List<String> third;
    }
}
