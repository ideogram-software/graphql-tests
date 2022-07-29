import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.expediagroup.graphql.plugin.gradle.tasks.GraphQLIntrospectSchemaTask

plugins {
	id("org.springframework.boot") version "2.6.8"
	id("io.spring.dependency-management") version "1.0.12.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
	id("com.expediagroup.graphql") version "5.5.0"
}

group = "io.ekkles"
version = ""
java.sourceCompatibility = JavaVersion.VERSION_1_8
base.archivesBaseName="ekklesio"

repositories {
	mavenCentral()
	google()
}

dependencies {
	implementation("com.ibm.icu:icu4j:71.1")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("com.expediagroup:graphql-kotlin-spring-server:5.5.0")
	implementation("com.expediagroup:graphql-kotlin-hooks-provider:5.5.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}



tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}


tasks.withType<Jar>() {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	configurations["compileClasspath"].forEach { file: File ->
		from(zipTree(file.absoluteFile))
	}
	destinationDirectory.set(file("$rootDir/../../deployment/backend/api_v3"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}


graphql {
	schema {
		packages = listOf("io.ekkles")
	}
}

val graphqlIntrospectSchema by tasks.getting(GraphQLIntrospectSchemaTask::class) {
	endpoint.set("http://localhost:8080/graphql")
}
