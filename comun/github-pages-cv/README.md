# CV + repos destacados en **GitHub Pages**

Este manual está pensado para publicar tu CV (tipo portfolio) en `tuusuario.github.io` y mostrar **sólo los repos públicos que marques** con un *topic* de GitHub (por ejemplo: `showcase/web/otros`).

---

## 0) Qué vas a construir

- Una web estática (HTML/CSS/JS) publicada en **GitHub Pages**.
- Secciones típicas:
  - **Sobre mí** + **foto**
  - **Proyectos destacados** (repos con topic `showcase`)
  - **Todos mis repos** (públicos, con búsqueda y filtros por topic)

---

## 1) Requisitos que debes de tener dentro de tu ordenador (Windows / Linux)

### 1.1 Instalar Git

**Windows**

1. Descarga e instala **Git for Windows**.
2. Durante la instalación, deja los valores por defecto.
3. Abre *Git Bash* y comprueba:

   ```bash
   git --version
   ```

**Linux (Debian/Ubuntu)**

```bash
sudo apt update
sudo apt install -y git
git --version
```

**Linux (Fedora)**
```bash
sudo dnf install -y git
git --version
```

### 1.2 Instalar un editor (VS Code por ejemplo)

- Instala **Visual Studio Code** (Windows o Linux).
- Recomendado: extensión “GitHub Pull Requests and Issues”.

### 1.3 Instalar Node.js

No es obligatorio si vas a usar solo HTML/CSS/JS, pero si recomenddado.  
Sí es útil si más adelante quieres usar frameworks (Astro/React) o automatizar builds.

---

### 1.4 Entorno local para desarrollar con **Jekyll** (Linux / WSL2)

> Esto es el **entorno recomendado** para poder ejecutar tu GitHub Pages en local con:
> `bundle exec jekyll serve`

### A) Instalar Ruby + dependencias (Ubuntu/Debian o WSL2 Ubuntu)

```bash
sudo apt update
sudo apt install -y ruby-full build-essential zlib1g-dev
```

### B) Configurar gems en tu usuario (evita `sudo gem ...`)

```bash
echo 'export GEM_HOME="$HOME/gems"' >> ~/.bashrc
echo 'export PATH="$HOME/gems/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc
```

### C) Instalar Bundler y preparar Jekyll para GitHub Pages

Dentro de la carpeta del repo (`TUUSUARIO.github.io`):

```bash
gem install bundler

cat > Gemfile <<'EOF'
source "https://rubygems.org"
gem "jekyll", "~> 4.3"

gem "webrick"
EOF

bundle install
```

Crea un `_config.yml` mínimo (si no existe):

```bash
cat > _config.yml <<'EOF'
title: "Mi página"
EOF
```

### D) Ejecutar el servidor local

```bash
bundle exec jekyll serve
```

Obtendrás una salida similar a la siguiente:

```bash
LiveReload address: http://127.0.0.1:35729
Server address: http://127.0.0.1:4000
Server running... press ctrl-c to stop.
```

Abre en el navegador:

- http://localhost:4000

*(Opcional)* con recarga automática:

```bash
bundle exec jekyll serve --livereload
```

Obtendrás una salida similar a la siguiente:

```bash
LiveReload address: http://127.0.0.1:35729
Server address: http://127.0.0.1:4000
Server running... press ctrl-c to stop.
```

### E) Subir cambios a GitHub Pages

```bash
git add .
git commit -m "Actualiza web (Jekyll local + CSS separado)"
git push
```

---

## 2) Preparar tu cuenta de GitHub

1. Crea una cuenta en GitHub si no la tienes.
2. Decide tu *username* (ej.: `juanperez`). Tu web quedará así:
   - `https://juanperez.github.io`

---

## 3) Crear el repositorio de GitHub Pages

1. En GitHub, crea un repositorio nuevo con este nombre EXACTO:
   - **`TUUSUARIO.github.io`**
   - Ejemplo: `juanperez.github.io`
2. Marca el repo como **Public**.
3. (Opcional) Añade un `README`.

---

## 4) Clonar el repositorio en local

Abre terminal (Windows: Git Bash / PowerShell; Linux: Terminal) y ejecuta:

```bash
git clone https://github.com/TUUSUARIO/TUUSUARIO.github.io
cd TUUSUARIO.github.io
```

---

## 5) Crear estructura de carpetas

Dentro del repo, crea:

```
TUUSUARIO.github.io/
  index.html
  _config.yml
  Gemfile
  _layouts/
    default.html
  assets/
    css/
      styles.css
    img/
      perfil.jpg
```

- Pon tu foto en `assets/img/perfil.jpg` (puede ser `.png`).

---

## 6) Crear `index.html` + separar CSS (HTML por un lado, CSS en `assets/css/styles.css`)

Crea `index.html` con este contenido (después personalizas texto y enlaces):

> ✅ Cambia `GITHUB_USERNAME` por tu usuario real.

```html
---
layout: default
title: Tu Nombre — CV & Repos
---

<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Tu Nombre — CV & Repos</title>
  <meta name="description" content="CV y repositorios destacados en GitHub." />
  <link rel="stylesheet" href="assets/css/styles.css">
</head>

<body>
  <div class="container">
    <header class="topbar">
      <div>
        <h1 class="title">Tu Nombre</h1>
        <div class="subtitle">Estudiante de Desarrollo de Aplicaciones Multiplataforma (1º DAM)</div>
      </div>
      <nav style="display:flex;gap:10px">
        <a class="btn" href="https://www.linkedin.com" target="_blank">LinkedIn</a>
        <a class="btn" href="https://github.com/TUUSUARIO" target="_blank">GitHub</a>
        <a class="btn" href="mailto:tuemail@ejemplo.com">Email</a>
      </nav>
    </header>

    <section class="section">
      <h2>Sobre mí</h2>
      <div class="grid2">
        <div class="profile">
          <img src="assets/img/perfil.jpg" alt="Foto de perfil">
          <div class="bio">
            <p class="muted" style="margin:0">
              Escribe aquí 3–4 líneas: qué estás aprendiendo, qué te gusta, en qué quieres mejorar.
            </p>
            <div class="pillrow">
              <span class="pill">Java</span>
              <span class="pill">SQL</span>
              <span class="pill">HTML/CSS</span>
              <span class="pill">Git</span>
            </div>
          </div>
        </div>

        <div>
          <h3 style="margin:0 0 10px">Experiencia / Proyectos de clase</h3>
          <ul class="muted" style="margin:0;padding-left:18px">
            <li>Proyecto DAM: “App de Notas” (CRUD + BBDD)</li>
            <li>Proyecto Java: “Gestor de tareas” (POO + tests)</li>
          </ul>

          <h3 style="margin:16px 0 10px">Objetivo</h3>
          <p class="muted" style="margin:0">
            Ejemplo: “Busco prácticas y mejorar en backend con Java/Spring y bases de datos.”
          </p>
        </div>
      </div>
    </section>

    <section class="section">
      <h2>Proyectos destacados (topic: <code>showcase</code>)</h2>
      <div id="featured" class="cards"></div>
      <p class="muted" style="margin:10px 0 0">
        Para que un repo salga aquí, añade el topic <b>showcase</b> en GitHub.
      </p>
    </section>

    <section class="section">
      <h2>Mis repositorios</h2>
      <div class="toolbar">
        <input id="q" class="input" placeholder="Buscar repositorios..." />
        <select id="sort" class="select">
          <option value="updated">Ordenar: actividad reciente</option>
          <option value="stars">Ordenar: estrellas</option>
          <option value="name">Ordenar: nombre</option>
        </select>
        <button class="btn active" data-topic="all">Todos</button>
        <button class="btn" data-topic="showcase">showcase</button>
        <button class="btn" data-topic="web">web</button>
        <button class="btn" data-topic="backend">backend</button>
      </div>

      <div style="height:12px"></div>
      <div id="repos" class="cards"></div>
    </section>
  </div>

<script>
  const GITHUB_USERNAME = "TUUSUARIO"; // <- CAMBIA ESTO
  const FEATURED_TOPIC = "showcase";
  let allRepos = [];
  let activeTopic = "all";

  function formatDate(iso){
    const d = new Date(iso);
    return d.toLocaleDateString("es-ES", {year:"numeric", month:"short", day:"2-digit"});
  }
  function cardBorderByIndex(i){
    return ["border-blue","border-violet","border-emerald"][i % 3];
  }

  async function fetchRepos(){
    const url = `https://api.github.com/users/${GITHUB_USERNAME}/repos?per_page=100&sort=updated`;
    const res = await fetch(url, { headers: { "Accept":"application/vnd.github+json" }});
    if(!res.ok) throw new Error("No se pudo leer la API de GitHub. Revisa tu usuario.");
    const repos = await res.json();
    return repos.map(r => ({
      name: r.name,
      html_url: r.html_url,
      description: r.description || "",
      language: r.language || "—",
      stargazers_count: r.stargazers_count || 0,
      updated_at: r.updated_at,
      topics: r.topics || []
    }));
  }

  function renderFeatured(repos){
    const featured = repos.filter(r => r.topics.includes(FEATURED_TOPIC)).slice(0, 6);
    const el = document.getElementById("featured");
    el.innerHTML = featured.length ? "" : `<div class="muted">Aún no tienes repos con topic <b>${FEATURED_TOPIC}</b>.</div>`;

    featured.forEach((r, i) => {
      const topics = r.topics.slice(0, 4).map(t => `<span class="pill">${t}</span>`).join("");
      el.insertAdjacentHTML("beforeend", `
        <article class="card ${cardBorderByIndex(i)}">
          <h3>${r.name}</h3>
          <div class="muted">${r.description || "Sin descripción (añádela en GitHub 😉)"}</div>
          <div class="pillrow">
            <span class="pill featured">Featured</span>
            ${topics}
          </div>
          <div class="repoMeta">
            <span>⭐ ${r.stargazers_count}</span>
            <span>🧩 ${r.language}</span>
            <span>🕒 ${formatDate(r.updated_at)}</span>
          </div>
          <a class="btn" href="${r.html_url}" target="_blank" rel="noreferrer">Ver repo</a>
        </article>
      `);
    });
  }

  function applyFilters(repos){
    const q = (document.getElementById("q").value || "").toLowerCase();
    const sort = document.getElementById("sort").value;

    let out = repos.filter(r => {
      const matchesText = r.name.toLowerCase().includes(q) || (r.description||"").toLowerCase().includes(q);
      const matchesTopic = activeTopic === "all" ? true : r.topics.includes(activeTopic);
      return matchesText && matchesTopic;
    });

    if (sort === "updated") out.sort((a,b) => new Date(b.updated_at) - new Date(a.updated_at));
    if (sort === "stars") out.sort((a,b) => (b.stargazers_count||0) - (a.stargazers_count||0));
    if (sort === "name") out.sort((a,b) => a.name.localeCompare(b.name));
    return out;
  }

  function renderRepos(repos){
    const el = document.getElementById("repos");
    const list = applyFilters(repos);
    el.innerHTML = list.length ? "" : `<div class="muted">No hay resultados con esos filtros.</div>`;

    list.forEach((r, i) => {
      const topics = (r.topics && r.topics.length)
        ? r.topics.slice(0, 6).map(t => `<span class="pill">${t}</span>`).join("")
        : `<span class="pill">sin topics</span>`;

      el.insertAdjacentHTML("beforeend", `
        <article class="card ${cardBorderByIndex(i)}">
          <h3>${r.name}</h3>
          <div class="muted">${r.description || "Sin descripción"}</div>
          <div class="pillrow">${topics}</div>
          <div class="repoMeta">
            <span>⭐ ${r.stargazers_count}</span>
            <span>🧩 ${r.language}</span>
            <span>🕒 ${formatDate(r.updated_at)}</span>
          </div>
          <a class="btn" href="${r.html_url}" target="_blank" rel="noreferrer">Abrir</a>
        </article>
      `);
    });
  }

  function bindUI(){
    document.getElementById("q").addEventListener("input", () => renderRepos(allRepos));
    document.getElementById("sort").addEventListener("change", () => renderRepos(allRepos));
    document.querySelectorAll(".btn[data-topic]").forEach(btn => {
      btn.addEventListener("click", () => {
        document.querySelectorAll(".btn[data-topic]").forEach(b => b.classList.remove("active"));
        btn.classList.add("active");
        activeTopic = btn.dataset.topic;
        renderRepos(allRepos);
      });
    });
  }

  async function main(){
    bindUI();
    allRepos = await fetchRepos();
    renderFeatured(allRepos);
    renderRepos(allRepos);
  }

  main().catch(err => {
    document.getElementById("featured").innerHTML = `<div class="muted">${err.message}</div>`;
    document.getElementById("repos").innerHTML = `<div class="muted">${err.message}</div>`;
    console.error(err);
  });
</script>
</body>
</html>
```

### 6.1 Crear `assets/css/styles.css`

### 6.2 Crear el layout de Jekyll (`_layouts/default.html`)

Crea la carpeta y el archivo:

```bash
mkdir -p _layouts
```

Contenido de `_layouts/default.html`:

```html
<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>{{ page.title | default: site.title }}</title>
  <meta name="description" content="CV y repositorios destacados en GitHub." />
  <link rel="stylesheet" href="/assets/css/styles.css">
</head>
<body>
  {{ content }}
</body>
</html>
```



Crea la carpeta y el archivo:

```bash
mkdir -p assets/css
```

Contenido de `assets/css/styles.css`:

```css
:root{
  --bg:#ffffff;
  --text:#0f172a;
  --muted:#475569;
  --card:#ffffff;
  --line:#e2e8f0;
  --brand:#2563eb;
  --brand2:#8b5cf6;
  --shadow: 0 16px 50px rgba(2,6,23,.10);
  --radius: 18px;
}
*{box-sizing:border-box}
body{
  margin:0;
  font-family: ui-sans-serif,system-ui,-apple-system,Segoe UI,Roboto,Helvetica,Arial;
  background: var(--bg);
  color: var(--text);
}
.container{max-width:1100px;margin:0 auto;padding:28px 18px 60px}
.topbar{
  display:flex;align-items:flex-start;justify-content:space-between;gap:16px;
  padding:24px;border:2px solid var(--line);border-radius:var(--radius);
  box-shadow: var(--shadow);
}
.title{font-size:44px;line-height:1.05;margin:0}
.subtitle{margin:10px 0 0;color:var(--muted);font-size:18px}
.section{margin-top:22px;padding:22px;border:2px solid var(--line);border-radius:var(--radius)}
.grid2{display:grid;grid-template-columns:1.1fr .9fr;gap:18px}
@media (max-width: 900px){ .grid2{grid-template-columns:1fr} .title{font-size:36px}}
.profile{
  display:flex;gap:18px;align-items:stretch;
  border-radius:16px;overflow:hidden;border:2px solid #dbeafe;
}
.profile img{width:46%;object-fit:cover;display:block}
.profile .bio{padding:16px}
.pillrow{display:flex;flex-wrap:wrap;gap:8px;margin-top:10px}
.pill{
  padding:6px 10px;border-radius:999px;font-size:12px;
  border:2px solid var(--line);
  background:#fff;
  color: #0f172a;
}
.pill.featured{border-color: #bbf7d0; background:#f0fdf4; color:#166534}
.cards{display:grid;grid-template-columns:repeat(3,1fr);gap:14px}
@media (max-width: 950px){ .cards{grid-template-columns:repeat(2,1fr)} }
@media (max-width: 620px){ .cards{grid-template-columns:1fr} }
.card{
  background:var(--card);border-radius:16px;padding:14px;
  border:2px solid var(--line);
  box-shadow: 0 10px 30px rgba(2,6,23,.06);
  display:flex;flex-direction:column;gap:10px;
}
.card.border-blue{border-color:#bfdbfe}
.card.border-violet{border-color:#ddd6fe}
.card.border-emerald{border-color:#bbf7d0}
.muted{color:var(--muted)}
.toolbar{display:flex;gap:10px;flex-wrap:wrap;align-items:center;margin-top:8px}
.input{flex:1;min-width:220px;padding:10px 12px;border-radius:12px;border:2px solid var(--line)}
.select{padding:10px 12px;border-radius:12px;border:2px solid var(--line);background:#fff}
.btn{padding:10px 12px;border-radius:12px;border:2px solid var(--line);background:#fff;cursor:pointer;text-decoration:none}
.btn.active{border-color:#93c5fd;background:#eff6ff}
.repoMeta{display:flex;gap:10px;flex-wrap:wrap;align-items:center;font-size:12px;color:var(--muted)}
```



---

## 7) Marcar repositorios para que salgan en “Destacados” (topic `showcase`)

1. Entra en un repositorio tuyo.
2. En la derecha, en **About**, pulsa el engranaje (editar).
3. En **Topics**, añade:
   - `showcase`
4. Guarda.

💡 También puedes añadir topics como: `dam`, `java`, `sql`, `web`, `backend`.

---

## 8) Subir cambios a GitHub (deploy)

Dentro de la carpeta del repo:

```bash
git add .
git commit -m "Publica CV en GitHub Pages"
git push
```

---

## 9) Activar GitHub Pages (si no se activa solo)

Normalmente, con `TUUSUARIO.github.io` se activa automáticamente.

Si no:
1. Repo → **Settings**
2. **Pages**
3. “Build and deployment”:
   - Source: **Deploy from a branch**
   - Branch: `main` y carpeta `/ (root)`
4. Guarda

Tu web será:
- `https://TUUSUARIO.github.io`

---

## 10) Recuerda verificar

- [ ] Repo `TUUSUARIO.github.io` público
- [ ] `index.html` en la raíz
- [ ] Foto en `assets/img/perfil.jpg`
- [ ] 2 repos con topic `showcase`
- [ ] Descripción + README en repos destacados
- [ ] La web abre en `https://TUUSUARIO.github.io`

---

## 11) Problemas típicos

### La web no aparece
- Comprueba el nombre exacto del repo: `TUUSUARIO.github.io`
- Settings → Pages

### No salen topics / featured
- A veces la API no devuelve `topics` en todos los casos.
- Solución “pro”: generar un `repos.json` con GitHub Actions y servirlo local (pídemelo si lo quieres).

---


## 12) Simplificando todo un poco

  Tienes un proyecto basebase para poder comenzar a trabajar [github-pages-cv](files/github-pages-cv.zip).

## 13) Personalización

Personaliza tu currículumn sin olvidar lo que es. Debes de modificar el contenido de `index.html` y de `styles.css`, además de añadir tu imagen de `perfil.jpg o png`.
Recuerda actualizar tus enlaces de github, ....