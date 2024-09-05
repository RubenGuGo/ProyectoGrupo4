describe('Inicio Page', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5173')
  })

  it('Navega a la página de inicio y comprueba ambos botones', () => {
    // Verificar que el botón "Explorar Tipos" está presente y funciona
    cy.get('a.cta-button.primary-button')
      .should('have.attr', 'href', '/tipo')
      .click()
    cy.url().should('include', '/tipo')

    // Volver a la página de inicio
    cy.visit('http://localhost:5173')

    // Verificar que el botón "Ver Obras" está presente y funciona
    cy.get('a.cta-button.secondary-button')
      .should('have.attr', 'href', '/obra')
      .click()
    cy.url().should('include', '/obra')
  })
})