# DOCUMENTATION

Here you can find the documentation of the main stack used in this project.

## Ktlint

> Ktlint is an anti-bikeshedding Kotlin linter with built-in formatter.

[Ktlint] is an open source library from Pinterest which have several [integrations/wrappers]. This project make use of the [jlleitschuh/ktlint-gradle] Gradle plugin which automatically creates check and format tasks for project Kotlin sources. It supports different kotlin plugins and Gradle build caching.


Gradle _root/build.gradle_ configuration:

```
buildscript {
    ext {
        ...
        ktlint_version = "11.3.2"
    }
}

plugins {
    ...
    id "org.jlleitschuh.gradle.ktlint" version "$ktlint_version"
}

allprojects {
    apply plugin: "org.jlleitschuh.gradle.ktlint"
}
```

Once the project is synced then check these ktlint gradle tasks are available:

- `ktlintCheck`
- `ktlintFormat`

## Detekt

> Detekt is a static code analyzer for Kotlin.

[Detekt] helps you write cleaner Kotlin code so you can focus on what matters the most building amazing software.

Check [Detekt docs] for more info. Some interesting entries are:

- [Configuration for Compose]
- [Complexity rules]


Gradle _root/build.gradle_ configuration:

```
buildscript {
    ext {
        ...
        detekt_version = "1.23.0-RC3"
    }
}

plugins {
    ...
    id "io.gitlab.arturbosch.detekt" version "$detekt_version"
}

allprojects {
    apply plugin: "io.gitlab.arturbosch.detekt"

    dependencies {
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detekt_version")
    }
}
```

Once the project is synced then check these detekt gradle tasks are available:

- `detektGenerateConfig` (For initial detekt.yml file creation)
- `detektBaseline` (For creating a detekt-baseline.xml if needed)
- `detekt`

### Run detekt using a Git pre-commit hook

Detekt can be integrated into your development workflow by using a Git pre-commit hook. For that reason Git supports to run custom scripts automatically, when a specific action occurs.

The shell script can be installed by copying the content over to `<<your-repo>>/.git/hooks/pre-commit`. This pre-commit hook needs to be executable, so you may need to change the permission (`chmod +x pre-commit`)

<details>
  <summary>Show pre-commit script</summary>

```
#!/usr/bin/env bash
echo "Running detekt check..."
OUTPUT="/tmp/detekt-$(date +%s)"
./gradlew detekt > $OUTPUT
EXIT_CODE=$?
if [ $EXIT_CODE -ne 0 ]; then
  cat $OUTPUT
  rm $OUTPUT
  echo "***********************************************"
  echo "                 Detekt failed                 "
  echo " Please fix the above issues before committing "
  echo "***********************************************"
  exit $EXIT_CODE
fi
rm $OUTPUT
```

**Note:** The pre-commit hook verification can be skipped for a certain commit like so:

`git commit --no-verify -m "commit message"` or
`git commit -n -m "commit message"`
</details>

<details>
  <summary>Show other pre-commit script with detekt + ktlint checks</summary>

```
#!/usr/bin/env bash
echo "Running detekt check..."
OUTPUT="/tmp/detekt-$(date +%s)"
OUTPUT_2="/tmp/ktlint-$(date +%s)"

./gradlew detekt > $OUTPUT
EXIT_CODE=$?

./gradlew ktlintCheck > $OUTPUT_2
EXIT_CODE_2=$?

if [ $EXIT_CODE -ne 0 ]; then
  cat $OUTPUT
  rm $OUTPUT
  echo "***********************************************"
  echo "                 Detekt failed                 "
  echo " Please fix the above issues before committing "
  echo "***********************************************"
  exit $EXIT_CODE
fi
rm $OUTPUT

if [ $EXIT_CODE_2 -ne 0 ]; then
  cat $OUTPUT_2
  rm $OUTPUT_
  echo "***********************************************"
  echo "                 Detekt failed                 "
  echo " Please fix the above issues before committing "
  echo "***********************************************"
  exit $EXIT_CODE
fi
rm $OUTPUT_2
```
</details>

## Navigation

> The Navigation component provides support for Jetpack Compose applications. You can navigate between composables while taking advantage of the Navigation component’s infrastructure and features.

Add the dependency to your _.toml_ file. Additionally you can add the material icons dependencies to your project. Thus you can use the icons in your navigation AppBar.
```
[versions]
navigation-compose = "2.5.3"

[libraries]
navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigation-compose" }
material-icons-core = { group = "androidx.compose.material3", name = "material-icons-core" }
material-icons-extended = { group = "androidx.compose.material3", name = "material-icons-extended" }

```

The Navigation component has three main parts:

- **NavController**: Responsible for navigating between destinations—that is, the screens in your app.
- **NavGraph**: Maps composable destinations to navigate to.
- **NavHost**: Composable acting as a container for displaying the current destination of the NavGraph.

For further information check the [Navigation docs](https://developer.android.com/jetpack/compose/navigation) and [Navigation codelab](https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation)

## Compose Lints

> Compose Lints is a collection of custom lint checks for Jetpack Compose, mostly ported from the original twitter/compose-rules project.
> 
> These checks are to ensure that your composables don’t fall into common pitfalls that may be easy to miss in code reviews.

<p><a href="https://mvnrepository.com/artifact/com.slack.lint.compose/compose-lint-checks"><img alt="Maven Central" src="https://img.shields.io/maven-central/v/com.slack.lint.compose/compose-lint-checks.svg" /></a></p>

```
// libs.versions.toml 

[versions]
compose-lint = "1.2.0"

[libraries]
compose-lint = { module = "com.slack.lint.compose:compose-lint-checks", version.ref = "compose-lint" }
```

```
// app/build.gradle.kts

dependencies {
    lintChecks(libs.compose.lint)
}
```

Check the [Compose Lints docs](https://slackhq.github.io/compose-lints/) for more info.


[//]: # (Document links)

[Ktlint]: <https://pinterest.github.io/ktlint/>
[integrations/wrappers]: <https://pinterest.github.io/ktlint/install/integrations/>
[jlleitschuh/ktlint-gradle]: <https://github.com/jlleitschuh/ktlint-gradle>
[Detekt]: <https://detekt.dev/>
[Detekt docs]: <https://detekt.dev/docs/intro/>
[Configuration for Compose]: <https://detekt.dev/docs/introduction/compose>
[Complexity rules]: <https://detekt.dev/docs/rules/complexity>
