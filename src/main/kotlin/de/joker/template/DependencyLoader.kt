package de.joker.template

import com.google.gson.Gson
import io.papermc.paper.plugin.loader.PluginClasspathBuilder
import io.papermc.paper.plugin.loader.PluginLoader
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver
import de.joker.template.model.PaperLib
import org.eclipse.aether.artifact.DefaultArtifact
import org.eclipse.aether.graph.Dependency
import org.eclipse.aether.repository.RemoteRepository
import java.io.InputStreamReader

@Suppress( "UnstableApiUsage", "UnstableApiUsage", "UnstableApiUsage", "UnstableApiUsage", "UnstableApiUsage",
    "UnstableApiUsage", "UnstableApiUsage"
)
class DependencyLoader : PluginLoader {
    override fun classloader(classpathBuilder: PluginClasspathBuilder) {
        val gson = Gson()
        val libs = javaClass.classLoader.getResourceAsStream("paper-libraries.json")

        libs?.let {

            val lib = gson.fromJson(InputStreamReader(libs), PaperLib::class.java)


            val resolver = MavenLibraryResolver()

            println("Loading libraries from paper-libraries.json")


            println("Libraries: $lib")
            lib.repositories.forEach { (name, url) ->
                resolver.addRepository(RemoteRepository.Builder(name, "default", url).build())
            }
            lib.dependencies.forEach {
                resolver.addDependency(Dependency(DefaultArtifact(it), null))
            }
            classpathBuilder.addLibrary(resolver)
        }
    }
}