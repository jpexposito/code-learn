<div align="justify">

# <img src=.../../../../images/computer.png width="40"> Code & Learn (Comandos de Git)


<div align="center">
<img src=https://www.freecodecamp.org/espanol/news/content/images/size/w2000/2021/01/cover-pic-1-.jpeg width="400">
</div>

## Configuración Básica

Configurar Nombre que salen en los commits

```ssh
	git config --global user.name "dasdo"
```

Configurar Email

```ssh	
	git config --global user.email dasdo1@gmail.com
```

Marco de colores para los comando

```ssh
	git config --global color.ui true
```

## Iniciando repositorio

Iniciamos GIT en la carpeta donde esta el proyecto

```ssh
	git init
```

Clonamos el repositorio de github o bitbucket

```ssh
	git clone <url>
```

Añadimos todos los archivos para el commit

```ssh
	git add .
```

Hacemos el primer commit

```ssh
	git commit -m "Texto que identifique por que se hizo el commit"
```

subimos al repositorio

```ssh
	git push origin master
```

## GIT CLONE

Clonamos el repositorio de github o bitbucket

```ssh
	git clone <url>
```

Clonamos el repositorio de github o bitbucket ?????

```ssh
	git clone <url> git-demo
```

## GIT ADD

Añadimos todos los archivos para el commit

```ssh
	git add .
```
Añadimos el archivo para el commit

```ssh
	git add <archivo>
```

Añadimos todos los archivos para el commit omitiendo los nuevos

```ssh
	git add --all 
```

Añadimos todos los archivos con la extensión especificada

```ssh
	git add *.txt
```

Añadimos todos los archivos dentro de un directorio y de una extensión especifica

```ssh
	git add docs/*.txt
```

Añadimos todos los archivos dentro de un directorios

```ssh
	git add docs/
```

## GIT COMMIT

Cargar en el HEAD los cambios realizados

```ssh
	git commit -m "Texto que identifique por que se hizo el commit"
```

Agregar y Cargar en el HEAD los cambios realizados

```ssh
	git commit -a -m "Texto que identifique por que se hizo el commit"
```

De haber conflictos los muestra

```ssh
	git commit -a 
```

Agregar al ultimo commit, este no se muestra como un nuevo commit en los logs. Se puede especificar un nuevo mensaje

```ssh
	git commit --amend -m "Texto que identifique por que se hizo el commit"
```

## GIT PUSH

Subimos al repositorio

```ssh
	git push <origien> <branch>
```

Subimos un tag

```ssh
	git push --tags
```

## GIT LOG

Muestra los logs de los commits

```ssh
	git log
```

Muestras los cambios en los commits

```ssh
	git log --oneline --stat
```

Muestra graficos de los commits

```ssh
	git log --oneline --graph
```

## GIT DIFF

Muestra los cambios realizados a un archivo

```ssh
	git diff
	git diff --staged
```

## GIT RESET

El comando git reset se utiliza para deshacer cambios en Git de manera más drástica que git revert. Dependiendo de la opción utilizada, puede cambiar el historial de commits, modificar el índice (staging area) o el estado del directorio de trabajo.

**git reset** es útil cuando necesitas mover el **puntero HEAD** a un commit anterior y ajustar el contenido del área de trabajo y del índice en función de dicho commit.

### Tipos de git reset

- **git reset --soft**. Solo mueve el **puntero HEAD a un commit anterior**, pero mantiene los cambios en el índice (staging area) y el directorio de trabajo. Esto te permite volver a realizar un commit con los cambios.

```bash
git reset --soft <hash_del_commit>
```

- **git reset --mixed (por defecto)**. Mueve el puntero HEAD y también borra los cambios del área de stage, pero mantiene los archivos modificados en el directorio de trabajo. Este es el comportamiento predeterminado si no se especifica una opción.

```bash
git reset --mixed <hash_del_commit>
```

- **git reset --hard**: Este es el tipo más destructivo, ya que mueve el puntero HEAD, elimina los cambios del índice y también del directorio de trabajo. Los archivos modificados y no confirmados serán eliminados permanentemente.

```bash
git reset --hard <hash_del_commit>
```

#### Ejemplo:

Si deseas mover HEAD al commit con el hash abc1234 y eliminar los cambios no confirmados, podrías usar:

```bash
git reset --hard abc1234
```

Diferencias clave entre las opciones de git reset:
- --soft: Conserva los cambios en el stage.
- --mixed: Mantiene los archivos modificados, pero los elimina del stage.
- --hard: Elimina todos los cambios no confirmados.
El uso de git reset debe hacerse con precaución, especialmente con la opción --hard, ya que los cambios descartados no se pueden recuperar fácilmente.

## GIT HEAD

Saca un archivo del commit

```ssh
	git reset HEAD <archivo>
```

Devuelve el ultimo commit que se hizo y pone los cambios en staging

```ssh
	git reset --soft HEAD^
```

Devuelve el ultimo commit y todos los cambios

```ssh
	git reset --hard HEAD^
```

Devuelve los 2 ultimo commit y todos los cambios

```ssh
	git reset --hard HEAD^^
```

Rollback merge/commit

```ssh
	git log
	git reset --hard <commit_sha>
```

## GIT REMOTE

Agregar repositorio remoto

```ssh
	git remote add origin <url>
```

Cambiar de remote

```ssh
	git remote set-url origin <url>
```

Remover repositorio

```ssh
	git remote rm <name/origin>
```

Muestra lista repositorios

```ssh
	git remote -v
```

Muestra los branches remotos

```ssh	
	git remote show origin
```

Limpiar todos los branches eliminados

```ssh
	git remote prune origin 
```

## GIT BRANCH

Crea un branch

```ssh
	git branch <nameBranch>
```

Lista los branches

```ssh
	git branch
```

Comando -d elimina el branch y lo une al master

```ssh
	git branch -d <nameBranch>
```

Elimina sin preguntar

```ssh
	git branch -D <nameBranch>
```

## GIT TAG

Muestra una lista de todos los tags

```ssh
	git tag
```

Crea un nuevo tags

```ssh
	git tag -a <verison> - m "esta es la versión x"
```

## GIT REBASE

Los rebase se usan cuando trabajamos con branches esto hace que los branches se pongan al día con el master sin afectar al mismo

Une el branch actual con el mastar, esto no se puede ver como un merge

```ssh
	git rebase
```

Cuando se produce un conflicto no das las siguientes opciones:

cuando resolvemos los conflictos --continue continua la secuencia del rebase donde se pauso

```ssh	
	git rebase --continue 
```

Omite el conflicto y sigue su camino

```ssh
	git rebase --skip
```

Devuelve todo al principio del rebase

```ssh
	git reabse --abort
```

Para hacer un rebase a un branch en especifico

```ssh	
	git rebase <nameBranch>
```

## OTROS COMANDOS

Lista un estado actual del repositorio con lista de archivos modificados o agregados

```ssh
	git status
```

Quita del HEAD un archivo y le pone el estado de no trabajado

```ssh
	git checkout -- <file>
```

Crea un branch en base a uno online

```ssh
	git checkout -b newlocalbranchname origin/branch-name
```

Busca los cambios nuevos y actualiza el repositorio

```ssh
	git pull origin <nameBranch>
```

Cambiar de branch

```ssh
	git checkout <nameBranch/tagname>
```

Une el branch actual con el especificado

```ssh
	git merge <nameBranch>
```

Verifica cambios en el repositorio online con el local

```ssh
	git fetch
```

Borrar un archivo del repositorio

```ssh
	git rm <archivo> 
```

## Fork

Descargar remote de un fork

```code
    git remote add upstream <url>
```

Merge con master de un fork

```code
    git fetch upstream
    git merge upstream/master
```

## Revert

El comando git revert se utiliza para deshacer los cambios introducidos por un commit específico, creando un nuevo commit que revierta esos cambios. A diferencia de git reset, que borra el historial de cambios, git revert mantiene el historial intacto, lo que es útil cuando trabajas con un equipo y no quieres eliminar los cambios ya compartidos.

```bash
git revert <hash_del_commit>
```

### Pasos para realizar un git revert

#### Obtén el hash del commit a revertir

Para encontrar el hash del commit que deseas revertir, puedes usar el comando:

```bash
git log
```

Esto mostrará el historial de commits con sus hashes. El hash es una cadena alfanumérica que identifica de forma única cada commit.

#### Revertir el commit

Una vez que tienes el hash del commit, puedes ejecutar el comando git revert seguido del hash del commit que deseas deshacer.

```bash
git revert <hash_del_commit>
```

Por ejemplo, si el hash del commit es ***abc1234***, ejecuta:

```bash
git revert abc1234
```

***Esto creará un nuevo commit que revierte los cambios realizados por el commit especificado***.

#### Resolver conflictos (si los hay)

Si el commit que intentas revertir entra en conflicto con otros cambios, Git te pedirá que resuelvas esos conflictos antes de completar el revert. Una vez resueltos los conflictos, añade los cambios con git add y finaliza el revert con un commit.

```bash
git add <archivo_resuelto>
git revert --continue
```

#### Comprobar el historial

Después de realizar el git revert, puedes ver el nuevo commit en el historial con:

```bash
git log
```

Verás un nuevo commit que revertirá los cambios del commit original, pero mantendrá el historial intacto.

##### Ejemplo práctivo

Imagina que hiciste un commit con el hash abc1234, pero te das cuenta de que cometiste un error y deseas revertir ese commit.

```bash
# Ver el historial de commits para obtener el hash del commit
git log

# Revertir el commit con el hash `abc1234`
git revert abc1234
```

> Esto creará un nuevo commit que deshará los cambios realizados por ***abc1234***, pero sin eliminar el commit original del historial.

### Revertir un merge

Si deseas revertir una fusión de ramas, debes especificar qué padre del merge quieres mantener utilizando la opción **-m**:

```bash
git revert -m 1 <hash_del_merge_commit>
```

Donde **-m 1** indica que estás revirtiendo la fusión manteniendo los cambios de la primera rama (normalmente, main o master).

En resumen, ***git revert es una manera segura de deshacer cambios en un repositorio, ya que no altera el historial, sino que crea un nuevo commit que deshace los cambios no deseados***.

## cherry-pick

El comando git cherry-pick **permite aplicar los cambios de un commit específico a la rama actual**. Es útil cuando deseas tomar uno o varios commits seleccionados de otra rama sin fusionar la rama completa.

A diferencia de otros comandos como git merge o git rebase, git cherry-pick solo copia el contenido de un commit específico y lo aplica como un nuevo commit en la rama actual, manteniendo intacta la historia del repositorio.

### Pasos para usar git cherry-pick

Obtener el hash del commit que deseas aplicar:

Para encontrar el commit que quieres "recoger", utiliza:

```bash
git log
```

Esto mostrará el historial de commits con sus hashes. El hash es una cadena alfanumérica que identifica cada commit de manera única.

Seleccionar y aplicar el commit:

Una vez que tengas el hash del commit que deseas aplicar, puedes ejecutar:

```bash
git cherry-pick <hash_del_commit>
```

Por ejemplo, si el hash del commit es **abc1234**, usarías:

```bash
git cherry-pick abc1234
```

***Esto aplicará los cambio de ese commit a la rama en la que estás trabajando actualmente, creando un nuevo commit con los mismos cambios***.

</div>