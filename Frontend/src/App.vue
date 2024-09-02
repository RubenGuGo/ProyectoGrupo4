<script setup>
import { RouterLink, RouterView } from 'vue-router';
</script>

<template>
  <div class="app-container">
    <nav class="navbar">
      <RouterLink active-class="active" to="/">Inicio</RouterLink>
      <RouterLink active-class="active" to="/tipo">Tipos de Obras</RouterLink>
      <RouterLink active-class="active" to="/obra">Obras</RouterLink>
    </nav>
    <div class="main-content">
      <RouterView />
    </div>
  </div>
</template>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  overflow-x: hidden; /* Evita el desplazamiento horizontal */
}

.navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #fff;
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
  text-align: center;
  z-index: 1000;
}

.navbar a {
  margin: 0 15px;
  text-decoration: none;
  color: #333;
}

.navbar a.active {
  font-weight: bold;
  color: #000;
}

.main-content {
  margin-top: 60px; /* Ajusta este valor según la altura de tu barra de navegación */
  flex: 1;
  overflow-x: hidden; /* Evita el desplazamiento horizontal */
}
</style>