# Cable [![CircleCI](https://circleci.com/gh/transferwise/cable/tree/master.svg?style=shield)](https://circleci.com/gh/transferwise/cable/tree/master) [![GitHub release](https://jitpack.io/v/transferwise/cable.svg)](https://github.com/transferwise/cable/releases/latest)

A simple and minimalistic url rewrite module.

## Installation

Just add the following configuration to your `build.gradle` file

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    compile 'com.github.transferwise.cable:cable-starter:2.0.0'
}
```

If you want to use the servlet filters by your own avoiding auto-configuration, just include the `cable-core` dependency

```gradle
compile 'com.github.transferwise.cable:cable-core:2.0.0'
```

## Configuration

Using the starter module you can easily add new rewrite rules to your `application.yml`

```yaml
cable:
  rewrites:
    - {from: "/path", to: "/more/interesting/path"}
    - {from: "^/([a-z]{2})/(.*)$", to: "/$1/blog/$2"}
}
```

## Usage

In Thymeleaf templates it will work automatically with link url expressions `@{...}`

```html
<a th:href="@{/test}">This is a test</a>
```

It will also automatically work on redirects within your controllers.

## Advanced usage

You can use lambda functions tu create more complex filters, so you will rewrite the url under controlled circumstances.

Let's imagine you want to rewrite only the urls of requests served under "/path":


```java 
@Bean
public Filter urlRewriteFilter() {
    return new UrlRewriteFilter()
        .rewrite("/test", "/test2", r -> r.getRequestURI().contains("/path"));
}
``` 
