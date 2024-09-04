<template>
  <main class="inicio">
    <h1>Bienvenido al Museo de Arte</h1>
    <p>Descubre la belleza y la historia a través de nuestras colecciones.</p>
  </main>
</template>

<style scoped>
.inicio {
  text-align: center;
  margin-top: 0;
  height: 100vh; /* Ocupa toda la altura de la pantalla */
  background-image: url('../assets/FondoMonet.jpg'); /* Ruta a tu imagen de fondo */
  background-size: cover; /* Asegura que la imagen cubra toda la pantalla */
  background-position: center; /* Centra la imagen */
  background-repeat: no-repeat; /* Evita que la imagen se repita */
  display: flex; /* Utiliza flexbox para centrar el contenido */
  flex-direction: column; /* Alinea los elementos en columna */
}

.inicio h1 {
  font-size: 2.5em;
  margin-bottom: 20px;
  color: white; /* Cambia el color del texto para que sea visible sobre la imagen */
}

.inicio p {
  font-size: 1.2em;
  color: white; /* Cambia el color del texto para que sea visible sobre la imagen */
}
</style>