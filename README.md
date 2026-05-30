# Sistema de Gestión de Estructuras Jerárquicas con Árboles
## División Territorial — Programación 3

 [Tablero Trello](https://trello.com/b/rCPB64Bp/proyecto-final-progra)

---

## Comandos de arranque rápido (para la demo)

 Ejecutar desde la raíz del proyecto. Swagger disponible en el enlace de cada combinación.

### Collections + Memory
```cmd
docker compose --profile memory up -d --build
```
 Swagger: http://localhost:8082/swagger-ui/index.html

### Custom + Memory
```cmd
docker compose --profile memory-custom up -d --build
```
 Swagger: http://localhost:8085/swagger-ui/index.html

### Collections + PostgreSQL
```cmd
docker compose --profile postgres up -d --build
```
 Swagger: http://localhost:8082/swagger-ui/index.html

### Custom + PostgreSQL
```cmd
docker compose --profile postgres-custom up -d --build
```
 Swagger: http://localhost:8083/swagger-ui/index.html

### Collections + MongoDB
```cmd
docker compose --profile mongo up -d --build
```
 Swagger: http://localhost:8082/swagger-ui/index.html

### Custom + MongoDB
```cmd
docker compose --profile mongo-custom up -d --build
```
 Swagger: http://localhost:8084/swagger-ui/index.html

---

##  Referencia completa de comandos Docker

### Ver contenedores
```cmd
docker ps
docker ps -a
```

### Apagar combinaciones
```cmd
# Collections + PostgreSQL
docker stop territorial-tree-app-postgres territorial-tree-postgres

# Custom + PostgreSQL
docker stop territorial-tree-app-postgres-custom territorial-tree-postgres

# Collections + MongoDB
docker stop territorial-tree-app-mongo territorial-tree-mongo

# Custom + MongoDB
docker stop territorial-tree-app-mongo-custom territorial-tree-mongo

# Collections + Memory
docker stop territorial-tree-app-memory

# Custom + Memory
docker stop territorial-tree-app-memory-custom
```

### Encender contenedores ya creados
```cmd
# Collections + PostgreSQL
docker start territorial-tree-postgres territorial-tree-app-postgres

# Custom + PostgreSQL
docker start territorial-tree-postgres territorial-tree-app-postgres-custom

# Collections + MongoDB
docker start territorial-tree-mongo territorial-tree-app-mongo

# Custom + MongoDB
docker start territorial-tree-mongo territorial-tree-app-mongo-custom

# Collections + Memory
docker start territorial-tree-app-memory

# Custom + Memory
docker start territorial-tree-app-memory-custom
```

### Ver logs
```cmd
docker logs territorial-tree-app-postgres --tail 120
docker logs territorial-tree-app-postgres-custom --tail 120
docker logs territorial-tree-app-mongo --tail 120
docker logs territorial-tree-app-mongo-custom --tail 120
docker logs territorial-tree-app-memory --tail 120
docker logs territorial-tree-app-memory-custom --tail 120
```

### Revisar datos en base de datos
```cmd
# PostgreSQL
docker exec -it territorial-tree-postgres psql -U postgres -d territorial_tree_db
SELECT * FROM nodes;
\q

# MongoDB
docker exec -it territorial-tree-mongo mongosh
use territorial_tree_db
show collections
db.nodes.find().pretty()
```

### Solución de errores comunes
```cmd
# Error de red
docker compose down --remove-orphans
docker network prune -f

# Contenedor viejo no enciende (ejemplo con mongo)
docker rm -f territorial-tree-app-mongo territorial-tree-mongo
docker compose --profile mongo up -d --build

#  CUIDADO: esto borra todos los datos guardados
docker compose down -v
```

---

## Descripción

Backend en Spring Boot que gestiona un árbol jerárquico de División Territorial (País → Departamento → Municipio → Aldea) con dos implementaciones intercambiables del motor de algoritmos y tres persistencias. Todo se elige por configuración sin recompilar.

---

## Estructura del proyecto

```
Proyecto-final-progra3/
├── tree-lib/          # Módulo del motor de algoritmos (dependencia Maven)
│   └── src/main/java/com/arbol/tree_lib/
│       ├── model/         TreeNode.java
│       ├── builder/       TreeBuilder.java, NodeFlat.java
│       ├── operations/    TreeOperations.java
│       └── strategy/      TreeAlgorithmStrategy.java
│                          CollectionsTreeStrategy.java
│                          CustomTreeStrategy.java
└── backend/           # Aplicación Spring Boot
    └── src/main/java/com/app/backend/
        ├── controller/    TreeController.java
        ├── service/       TreeService.java
        ├── config/        CollectionsCondition.java, CustomCondition.java
        │                  MongoCondition.java, MemoryCondition.java
        │                  TreeStrategyConfig.java
        ├── model/         Node.java
        └── repository/
            ├── MemoryRepository.java
            ├── mongo/     MongoTreeRepository.java
            └── postgres/  PostgresRepository.java
```

---

## Cómo ejecutar

### Requisitos previos
- Java 17
- Maven 3.8+
- Docker (para MongoDB o PostgreSQL)

### Perfil memory (sin base de datos)
```cmd
cd backend
mvn spring-boot:run
```

### Perfil MongoDB
```cmd
docker-compose -f docker-compose.mongo.yml up -d
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=mongo
```

### Perfil PostgreSQL
```cmd
docker-compose -f docker-compose.postgres.yml up -d
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

La app corre en `http://localhost:8082`

---

## Selectores de configuración

| Propiedad | Valores | Descripción |
|-----------|---------|-------------|
| `app.storage` | `memory`, `postgres`, `mongo` | Persistencia activa |
| `app.tree-strategy` | `collections`, `custom` | Motor de algoritmos activo |

Ejemplo de combinaciones:
- `collections + memory` → estrategia JDK, sin base de datos
- `custom + postgres` → estructura propia, PostgreSQL
- `collections + mongo` → estrategia JDK, MongoDB

---

## API REST — Operaciones del árbol

Base URL: `http://localhost:8082`

| # | Operación | Método | Endpoint |
|---|-----------|--------|----------|
| 1 | Crear raíz | POST | `/nodes/root` |
| 2 | Agregar hijo | POST | `/nodes/{parentId}/children` |
| 3 | Árbol completo | GET | `/tree` |
| 4 | Subárbol | GET | `/tree/{nodeId}` |
| 5 | Ruta desde raíz | GET | `/nodes/{nodeId}/path` |
| 6 | Recorrido DFS | GET | `/tree/traversal?type=DFS` |
| 7 | Recorrido BFS | GET | `/tree/traversal?type=BFS` |
| 8 | Altura del árbol | GET | `/tree/height` |
| 9 | Profundidad de un nodo | GET | `/nodes/{nodeId}/depth` |
| 10 | Ancestros de un nodo | GET | `/nodes/{nodeId}/ancestors` |
| 11 | Validar sin ciclos | GET | `/tree/validate` |

### Ejemplo de body para crear nodo
```json
{ "value": "Guatemala" }
```

---

## Motor de algoritmos — Estrategia Custom

**Clase:** `CustomTreeStrategy.java`
**Activación:** `app.tree-strategy=custom`

Esta implementación resuelve las 11 operaciones usando únicamente estructuras propias, sin Collections del JDK como estructura principal.

### Estructuras propias utilizadas

| Estructura | Uso |
|------------|-----|
| `TreeNode[]` con redimensión manual | Array dinámico de hijos en cada nodo |
| `TreeNode[] queue` con punteros `front`/`rear` | Cola para BFS |
| `TreeNode[] path` con contador `int[] size` | Pila para backtracking en ruta/ancestros |
| `TreeNode[] result` con contador `int[] size` | Acumulador de resultados en DFS |
| `TreeNode[] visited` con contador `int[] size` | Registro de nodos visitados para validar ciclos |

### Algoritmo por algoritmo

**DFS** — Recursión con acumulador manual:
```
dfsRecursive(nodo, result[], size[])
  result[size++] = nodo
  para cada hijo → dfsRecursive(hijo, result, size)
```

**BFS** — Cola manual con punteros front/rear:
```
queue[rear++] = raíz
mientras front < rear:
  current = queue[front++]
  result[size++] = current
  para cada hijo → queue[rear++] = hijo
```

**Altura** — Recursión comparando máximos sin Math:
```
altura(nodo):
  si no tiene hijos → 0
  max = 0
  para cada hijo:
    h = altura(hijo)
    si h > max → max = h
  retornar max + 1
```

**Profundidad** — Recursión con contador de nivel:
```
profundidad(nodo, nodeId, nivel):
  si nodo.id == nodeId → nivel
  para cada hijo → profundidad(hijo, nodeId, nivel+1)
```

**Ruta desde raíz** — Backtracking con arreglo manual:
```
findPath(nodo, nodeId, path[], size[]):
  path[size++] = nodo
  si nodo.id == nodeId → true
  para cada hijo:
    si findPath(hijo, ...) → true
  size-- (backtrack)
  retornar false
```

**Ancestros** — Ruta desde raíz sin incluir el nodo destino:
```
ancestors = pathFromRoot(nodeId)
retornar path[0 .. size-2]
```

**Validar ciclos** — Array de visitados con búsqueda lineal:
```
hasCycle(nodo, visited[], size[]):
  si nodo.id está en visited[] → true (ciclo)
  visited[size++] = nodo
  para cada hijo → hasCycle(hijo, visited, size)
  size-- (backtrack)
```

**Subárbol** — Búsqueda recursiva por ID:
```
findNode(nodo, nodeId):
  si nodo.id == nodeId → nodo
  para cada hijo → findNode(hijo, nodeId)
```

**Redimensión del array de hijos** en `TreeNode`:
```
resize():
  nuevo[] = new TreeNode[actual.length * 2]
  copiar elementos manualmente
  children = nuevo
```

---

## Motor de algoritmos — Estrategia Collections

**Clase:** `CollectionsTreeStrategy.java`
**Activación:** `app.tree-strategy=collections`

Resuelve las mismas 11 operaciones usando Collections estándar del JDK.

### Collections utilizadas

| Collection | Algoritmo donde se usa |
|------------|------------------------|
| `ArrayList<TreeNode>` | Acumulador de resultados en DFS, BFS, ruta, ancestros |
| `ArrayDeque<TreeNode>` | Cola para BFS (`Queue<TreeNode>`) |
| `HashSet<Long>` | Registro de IDs visitados para validar ciclos |

### Comparación con la estrategia Custom

| Operación | Collections | Custom |
|-----------|------------|--------|
| DFS | Recursión + `ArrayList` | Recursión + `TreeNode[]` manual |
| BFS | `ArrayDeque` como cola | Array manual con punteros `front`/`rear` |
| Altura | Recursión + `Math.max` | Recursión + comparación manual |
| Profundidad | Recursión con contador | Recursión con contador |
| Ruta | Recursión + `ArrayList` con backtrack | Recursión + `TreeNode[]` con backtrack |
| Ancestros | Ruta sin último elemento (`List`) | Ruta sin último elemento (`TreeNode[]`) |
| Validar ciclos | `HashSet<Long>` | `TreeNode[]` con búsqueda lineal |
| Subárbol | Recursión con `Optional` | Recursión con `Optional` |

Ambas implementan `TreeAlgorithmStrategy` y producen resultados equivalentes para las mismas entradas.

---

## Validación de equivalencia entre estrategias

Ambas estrategias se probaron con el mismo árbol de División Territorial:

```
Guatemala (id=1)
├── Guatemala (id=2)
│   ├── Guatemala (id=4)
│   └── Mixco (id=5)
└── Quetzaltenango (id=3)
    └── Quetzaltenango (id=6)
```

| Operación | Resultado Collections | Resultado Custom | ¿Equivalente? |
|-----------|----------------------|-----------------|---------------|
| DFS | [1,2,4,5,3,6] | [1,2,4,5,3,6] | ✅ |
| BFS | [1,2,3,4,5,6] | [1,2,3,4,5,6] | ✅ |
| Altura | 2 | 2 | ✅ |
| Profundidad nodo 4 | 2 | 2 | ✅ |
| Ruta al nodo 5 | [1,2,5] | [1,2,5] | ✅ |
| Ancestros nodo 5 | [1,2] | [1,2] | ✅ |
| Validar ciclos | true | true | ✅ |
| Subárbol nodo 2 | {id:2, hijos:[4,5]} | {id:2, hijos:[4,5]} | ✅ |

---

## Reparto del equipo

| Integrante | Persistencia | Motor | Transversal |
|------------|-------------|-------|-------------|
| A | Memoria | Estrategia `custom` | Interfaz del motor + OpenAPI + esqueleto multimódulo |
| B | PostgreSQL | Estrategia `collections` | Modelo ER + scripts SQL + datos de prueba |
| C | MongoDB | Integración Spring de ambas estrategias | Selectores `app.tree-strategy` y `app.storage` |
