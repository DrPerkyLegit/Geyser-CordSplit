# Geyser Extension Template
A Geyser Extension template for creating Geyser Extensions. 

## What are Geyser extensions?
Geyser Extensions are the equivalent of “plugins”, but specifically for the Geyser platform. This brings the advantage of them being platform-agnostic, meaning that you won’t have to worry about supporting all platforms individually. Additionally, they will be, by design, only applied for Bedrock players joining via Geyser.

## What can extensions do?
- Register [custom items](https://wiki.geysermc.org/geyser/custom-items/), [custom blocks](https://wiki.geysermc.org/geyser/custom-blocks/#geyser-extensions) and more in code!
- Listen and toggle various Bedrock features (i.e. commands)
- Send forms to Bedrock players using [Cumulus](https://github.com/GeyserMC/Cumulus)
- Listen and interact with [Events](https://wiki.geysermc.org/geyser/events/)

## Using this Template
Click "Use this template" on the top right, and create your own repo. 
Then, see the [usage guide](./USAGE.md) for instructions on how to use this template.

## Documentation
Our [wiki](https://geysermc.org/wiki/) has helpful articles. The following are recommended:
- Geyser Event System documentation: https://geysermc.org/wiki/geyser/events
- Geyser Forms / Cumulus documentation: https://geysermc.org/wiki/geyser/forms
- Brief overview on the Geyser API: https://geysermc.org/wiki/geyser/api
- Extension docs: https://geysermc.org/wiki/geyser/extensions

## Existing Extensions
See our list [here](https://github.com/GeyserMC/GeyserExtensionList).

## Suggestions?
Reach out on our [Discord](https://discord.gg/geysermc)!

## Important Notes
- `extension.yml` is required for Geyser to load the extension. It must be in the `resources` folder.
- Geyser Extensions: https://github.com/GeyserMC/Geyser/blob/master/api/src/main/java/org/geysermc/geyser/api/extension/Extension.java
- Geyser API javadocs: https://repo.opencollab.dev/javadoc/maven-snapshots/org/geysermc/geyser/api/latest

