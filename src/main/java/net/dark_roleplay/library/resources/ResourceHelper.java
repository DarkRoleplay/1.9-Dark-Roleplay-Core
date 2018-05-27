package net.dark_roleplay.library.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.io.IOUtils;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.ModContainer;

public class ResourceHelper {

	/**
	 * TODO replace with propper system once 1.13 is released
	 * @link{Copied out of net.minecraftforge.common.craftingCraftingHelper.class}
	 * @param mod
	 * @param base
	 * @param preprocessor
	 * @param processor
	 * @param defaultUnfoundRoot
	 * @param visitAllFiles
	 * @return
	 */
	@Deprecated
	public static boolean findFiles(ModContainer mod, String base, Function<Path, Boolean> preprocessor,
			BiFunction<Path, Path, Boolean> processor, boolean defaultUnfoundRoot, boolean visitAllFiles) {
		FileSystem fs = null;
		try {
			File source = mod.getSource();

			Path root = null;
			if (source.isFile()) {
				try {
					fs = FileSystems.newFileSystem(source.toPath(), null);
					root = fs.getPath("/" + base);
				} catch (IOException e) {
					FMLLog.log.error("Error loading FileSystem from jar: ", e);
					return false;
				}
			} else if (source.isDirectory()) {
				root = source.toPath().resolve(base);
			}

			if (root == null || !Files.exists(root))
				return defaultUnfoundRoot;

			if (preprocessor != null) {
				Boolean cont = preprocessor.apply(root);
				if (cont == null || !cont.booleanValue())
					return false;
			}

			boolean success = true;

			if (processor != null) {
				Iterator<Path> itr = null;
				try {
					itr = Files.walk(root).iterator();
				} catch (IOException e) {
					FMLLog.log.error("Error iterating filesystem for: {}", mod.getModId(), e);
					return false;
				}

				while (itr != null && itr.hasNext()) {
					Boolean cont = processor.apply(root, itr.next());

					if (visitAllFiles) {
						success &= cont != null && cont;
					} else if (cont == null || !cont) {
						return false;
					}
				}
			}

			return success;
		} finally {
			IOUtils.closeQuietly(fs);
		}
	}
}
