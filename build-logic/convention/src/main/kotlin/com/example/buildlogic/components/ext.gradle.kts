import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency


fun plugin(provider: Provider<PluginDependency>) = with(provider.get()) {
    "$pluginId:$pluginId.gradle.plugin:$version"
}