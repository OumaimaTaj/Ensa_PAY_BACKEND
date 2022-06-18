package com.example.demo.dto;


import com.example.demo.model.Agency;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AgencyDto {

    private Long id;

    @NotBlank
    private String title;

    private String address;
    private String image;
    private String service;

    public static Agency toEntity(AgencyDto agencyDto) {
        if (agencyDto == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            AgencyDto.class.getName()
                    )
            );
        }

        Agency agency = Agency.builder()
                .title(agencyDto.title)
                .address(agencyDto.getAddress())
                .image(agencyDto.getImage())
                .service(agencyDto.getService())
                .build();

        agency.setId(agencyDto.id);

        return agency;
    }

    public static AgencyDto fromEntity(Agency agency) {
        if (agency == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to a dto.",
                            Agency.class.getName()
                    )
            );
        }

        return AgencyDto.builder()
                .id(agency.getId())
                .title(agency.getTitle())
                .image(agency.getImage())
                .service(agency.getService())
                .build();
    }
}