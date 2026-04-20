package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.ImageFileDto;
import com.example.gestionimmobilier.Entity.ImageFile;

public interface IImageFileMapper {




    ImageFileDto fromImageFile(ImageFile imageFile);
    ImageFile fromImageFileDTO(ImageFileDto imageFileDto);
}
