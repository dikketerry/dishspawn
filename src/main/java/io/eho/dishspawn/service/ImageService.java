package io.eho.dishspawn.service;

import io.eho.dishspawn.model.Recipe;
import io.eho.dishspawn.model.Visual;

public interface ImageService {

//    String generateImage(Recipe recipe);
    GeneratedImage generateImage(Recipe recipe);
    Visual saveVisual(Recipe recipe, Long newId, byte[] pngBytes);
//    Visual saveVisual(Recipe recipe, Long newId);

    /**
     * Output of one spawn render:
     *  - previewBase64 : PNG as base64, for the preview page
     *  - pngBytes      : raw PNG, held by the caller (HTTP session) until the user saves
     */
    record GeneratedImage(String previewBase64, byte[] pngBytes) {}

}
