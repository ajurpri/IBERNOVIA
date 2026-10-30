import test from 'node:test'
import assert from 'node:assert/strict'
import { productName, productReference, sentenceCase, productWhatsAppUrl } from './productPresentation.js'

test('commercial reference is preserved independently from descriptive title', () => {
  const product = Object.freeze({id:639,nombre:'1239-AB',descripcion:'ABANICO NOVIA DE ENCAJE MARFIL.\nMEDIDAS: 23 CM. DE LARGO APROXIMADAMENTE.'})
  assert.equal(productName(product), 'Abanico de novia de encaje marfil')
  assert.equal(productReference(product), '1239-AB')
  assert.equal(product.nombre, '1239-AB')
})

test('dimensions and incomplete information never become an invented product name', () => {
  assert.equal(productName({nombre:'1388-CE',descripcion:'MEDIDAS: 20 CM. DE DIAMETRO'}), '1388-CE')
  assert.equal(productName({nombre:'20-A',descripcion:'ARRAS DE 22 MM. BIBLICAS.'}), '20-A')
  assert.equal(productName({nombre:'ABC'}), 'ABC')
  assert.equal(sentenceCase('Encaje de España\nMEDIDAS: 23 CM.'), 'Encaje de España\nMedidas: 23 cm.')
})

test('WhatsApp includes public reference and canonical URL, excludes prices and customer data', () => {
  const url = new URL(productWhatsAppUrl({id:639,nombre:'1239-AB',descripcion:'ABANICO DE NOVIA.',precio:9876.54,precioOferta:5432.1,cliente:'private@example.test'}))
  const text = url.searchParams.get('text')
  assert.equal(url.origin,'https://wa.me')
  assert.match(text,/Referencia: 1239-AB/)
  assert.match(text,/https:\/\/www\.ibernovia\.es\/producto\/639/)
  assert.doesNotMatch(text,/9876|5432|private@/)
})
