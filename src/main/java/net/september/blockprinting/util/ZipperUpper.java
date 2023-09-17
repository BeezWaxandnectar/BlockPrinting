package net.september.blockprinting.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.nio.file.FileSystems;

import static net.september.blockprinting.util.FileTracker.metaLoc;
import static net.september.blockprinting.util.FileTracker.youExist;

public class ZipperUpper {

    //Technically, this class doesn't need to exist. I could always manually compress each folder into zip files.
    //However, this would make it really annoying to iterate on the visuals as I go.

    private static int BUF_SIZE = 8192;
    private final Path source;
    private final Path destination;

    private static final Gson Gsonathan = new GsonBuilder()
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    public ZipperUpper(final Path source, final Path destination) {
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(destination, "destination");

        this.source = source;
        this.destination = destination;
    }

    public void execute() throws IOException {
        execute(source, destination);
    }

    public static void execute(final Path source, final Path destination) throws IOException{

        //Redundant??
            try (DirectoryStream<Path> overStream = Files.newDirectoryStream(source);
                 final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destination.toFile()))) {

                File metaSrc = metaLoc.toFile();
                zipThisFile(metaSrc, metaSrc.getName(), zos);

                //FileTracker.savePackMcmeta();

                for (Path path : overStream) {
                    File file = path.toFile();
                    zipThisFile(file, file.getName(), zos);
                }
                zos.finish();
            }
    }

    private static void zipThisFile(File fileToZip, String fileName, ZipOutputStream zos) throws IOException {
        if (!fileName.startsWith(FileTracker.packPrefix) && !fileName.endsWith("pack.mcmeta")){
            fileName = FileTracker.packPrefix + fileName;
        }

        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zos.putNextEntry(new ZipEntry(fileName));
            } else {
                zos.putNextEntry(new ZipEntry(fileName + "/"));
            }
            for (File childFile : fileToZip.listFiles()) {
                zipThisFile(childFile, fileName + "/" + childFile.getName(), zos);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[BUF_SIZE];
        int readout;
        while ((readout = fis.read(bytes)) >= 0){
            zos.write(bytes, 0, readout);
        }
        fis.close();
    }

    public static void savePackMcmeta(Path pPath) {
        Path path = pPath.resolve("pack.mcmeta");
        JsonObject meta = new JsonObject();
        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", 15);
        pack.addProperty("description", "This file defines BlockPrinting's resource pack.");
        meta.add("pack", pack);

        try {
            if (!youExist(path)) {
                Files.createDirectories(path.getParent());
            }
            String json = Gsonathan.toJson(meta);
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                bufferedWriter.write(json);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
