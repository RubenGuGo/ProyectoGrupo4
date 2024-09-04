<template>
  <main class="inicio">
    <div class="content">
      <h1>Bienvenido al Museo de Arte</h1>
      <p>Descubre la belleza y la historia a través de nuestras colecciones.</p>
      <div class="button-container">
        <a href="/tipo" class="cta-button primary-button">Explorar Tipos</a>
        <a href="/obra" class="cta-button secondary-button">Ver Obras</a>
      </div>
    </div>
  </main>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

.inicio {
  height: 100vh; /* Ocupa toda la altura de la pantalla */
  margin: 0;
  padding: 0;
  background-image: url('../assets/FondoMonet.jpg'); /* Ruta a tu imagen de fondo */
  background-size: cover; /* Asegura que la imagen cubra toda la pantalla */
  background-position: center; /* Centra la imagen */
  background-repeat: no-repeat; /* Evita que la imagen se repita */
  display: flex; /* Utiliza flexbox para centrar el contenido */
  flex-direction: column; /* Alinea los elementos en columna */
  justify-content: center; /* Centra el contenido verticalmente */
  align-items: center; /* Centra el contenido horizontalmente */
  font-family: 'Roboto', sans-serif; /* Aplica la fuente Roboto */
  color: white;
  text-align: center;
  overflow: hidden; /* Evita el scroll innecesario */
}

.content {
  background-color: rgba(0, 0, 0, 0.6); /* Fondo semi-transparente con mayor opacidad para mejorar la legibilidad */
  padding: 40px 60px;
  border-radius: 15px;
  max-width: 90%; /* Asegura que el contenido no se desborde en pantallas pequeñas */
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3); /* Añade una sombra para dar más profundidad */
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.content:hover {
  transform: translateY(-10px); /* Eleva ligeramente el contenedor al hacer hover */
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.5); /* Aumenta la sombra al hacer hover */
}

.inicio h1 {
  font-size: 3.5em;
  margin-bottom: 20px;
  color: white; /* Asegura que el texto sea visible sobre la imagen */
  text-shadow: 3px 3px 6px rgba(0, 0, 0, 0.7); /* Sombra más pronunciada para el texto */
  letter-spacing: 2px; /* Añade espacio entre letras para un toque moderno */
  transition: color 0.3s ease;
}

.inicio p {
  font-size: 1.7em;
  color: white;
  margin-bottom: 30px;
  text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.7);
}

.button-container {
  display: flex; /* Utiliza flexbox para alinear los botones */
  justify-content: center; /* Centra los botones horizontalmente */
  gap: 20px; /* Espacio entre los botones */
  margin-top: 20px; /* Espacio superior para separar los botones del texto */
}

.cta-button {
  display: inline-block;
  padding: 12px 20px;
  font-size: 1rem;
  color: white;
  text-decoration: none;
  border-radius: 8px;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.3);
  transition: background-color 0.3s ease, transform 0.2s ease, box-shadow 0.3s ease;
}

.cta-button.primary-button {
  background-color: #dc3545; /* Rojo para el botón de "Explorar Tipos" */
}

.cta-button.primary-button:hover {
  background-color: #c82333; /* Oscurece el color al hacer hover */
  transform: translateY(-2px); /* Eleva ligeramente el botón al hacer hover */
  box-shadow: 0 8px 15px rgba(220, 53, 69, 0.5); /* Sombra más pronunciada al hacer hover */
}

.cta-button.secondary-button {
  background-color: #007bff; /* Azul para el botón de "Ver Obras" */
}

.cta-button.secondary-button:hover {
  background-color: #0056b3; /* Oscurece el color al hacer hover */
  transform: translateY(-2px); /* Eleva ligeramente el botón al hacer hover */
  box-shadow: 0 8px 15px rgba(0, 123, 255, 0.5); /* Sombra más pronunciada al hacer hover */
}
</style>
