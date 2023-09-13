package ru.job4j.cars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class FileService {

    public final FileRepository fileRepository;
    private final String storageDirectory;

    public FileService(FileRepository fileRepository, @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
    }

    /**
     * Создание строки пути с использованием имени файла.
     * Запись в путь нового файла в байтах.
     * Добавление файла в БД.
     *
     * @param fileDto файл
     * @return файл
     */
    public File create(FileDto fileDto) {
        var path = getNewPhotoPath(fileDto.getName());
        writeFileBytes(path, fileDto.getPhoto());
        return fileRepository.create(new File(fileDto.getName(), path));
    }

    /**
     * Так создается уникальный путь для нового файла.
     * UUID это просто рандомная строка определенного формата.
     *
     * @param sourceName имя файла Dto
     * @return строка с указанием пути для нового файла.
     */
    private String getNewPhotoPath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    /**
     * Метод выполняет запись массива байтов(контент) по указанному пути.
     *
     * @param path    путь
     * @param content контент
     */
    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * поиск файла по id и запись его в DTO.
     *
     * @param id id
     * @return файл DTO.
     */
    public Optional<FileDto> findById(int id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    /**
     * Возвращает файл в byte[].
     *
     * @param path путь к файлу.
     * @return файл в byte[].
     */
    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(List<File> files) {
        for (File file: files) {
            deletePhoto(file.getId());
        }
    }

    public boolean deletePhoto(int photoId) {
        var fileOptional = fileRepository.findById(photoId);
        if (fileOptional.isEmpty()) {
            return false;
        }
        deleteFile(fileOptional.get().getPath());
        return fileRepository.delete(photoId);
    }

    /**
     * Метод выполняет удаление файла
     *
     * @param path путь файла
     */
    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Преобразование списка новых файлов в список <File> и добавлением в БД.
     *
     * @param files
     * @return
     * @throws IOException
     */
    public List<File> convertMultipartInFile(List<MultipartFile> files) throws IOException {
        List<File> savedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            File savedFile = create(new FileDto(file.getOriginalFilename(), file.getBytes()));
            savedFiles.add(savedFile);
        }
        savedFiles.sort(Comparator.comparing(File::getName));
        return savedFiles;
    }
}
