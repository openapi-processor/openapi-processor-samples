default:
  @just --list --unsorted

# update gradle wrapper
wrapper version="8.14.1":
    ./gradlew wrapper --gradle-version={{version}}

# purge openapiprocessor dependencies
purge:
  ./mvnw dependency:purge-local-repository -Dinclude:io.openapiprocessor -DresolutionFuzziness=groupId -Dverbose
