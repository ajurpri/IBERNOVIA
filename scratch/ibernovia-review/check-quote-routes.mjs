import { createRequire } from 'node:module'
import { pathToFileURL } from 'node:url'
import { resolve } from 'node:path'
import assert from 'node:assert/strict'

const project = resolve('ibernovias/front-end')
const require = createRequire(resolve(project, 'package.json'))
const { createServer } = await import(pathToFileURL(require.resolve('vite')).href)
const { createSSRApp, h } = require('vue')
const { renderToString } = require('@vue/server-renderer')
const server = await createServer({root: project, server: {middlewareMode:true}, appType:'custom'})
try {
  for (const page of ['CheckoutView', 'ConfirmacionView']) {
    const {default: component} = await server.ssrLoadModule(`/src/views/${page}.vue`)
    const app = createSSRApp(component)
    app.component('RouterLink', {props:['to'], setup: (props, {slots}) => () => h('a',{href:props.to},slots.default())})
    const html = await renderToString(app)
    assert.match(html, /Esta página no confirma un pedido ni un pago/)
    assert.match(html, /href="\/carrito"/)
    assert.doesNotMatch(html, /Compra Completada|Total Pagado|tu@email.com|IBV-/)
    console.log(`${page}: no false confirmation; selection link present`)
  }
} finally { await server.close() }
