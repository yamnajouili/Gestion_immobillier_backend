package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.ImageFileDto;
import com.example.gestionimmobilier.Entity.ImageFile;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageFileMapper implements IImageFileMapper{

    private final ModelMapper modelMapper;
    @Override
    public ImageFileDto fromImageFile(ImageFile imageFile) {
        return modelMapper.map(imageFile, ImageFileDto.class);

    }

    @Override
    public ImageFile fromImageFileDTO(ImageFileDto imageFileDto) {
        return modelMapper.map(imageFileDto, ImageFile.class);
    }
}
