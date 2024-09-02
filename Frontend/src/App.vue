<script setup>
import { RouterLink, RouterView } from 'vue-router';
</script>

<template>
  <main>
    <nav class="navbar">
      <RouterLink active-class="active" to="/">Inicio</RouterLink>
      <RouterLink active-class="active" to="/tipo">Tipos de Obras</RouterLink>
      <RouterLink active-class="active" to="/obra">Obras</RouterLink>
    </nav>
    <RouterView />
  </main>
</template>

<style scoped>
.navbar {
  position: fixed;
  top: 0;
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

main {
  padding-top: 80px; /* Ajusta este valor según la altura de tu barra de navegación */
}
</style>