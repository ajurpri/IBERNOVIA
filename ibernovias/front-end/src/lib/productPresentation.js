// Presentation only: keep the source catalog and its commercial references intact.
export const productReference = (product) => String(product?.nombre || '').trim()

export const sentenceCase = (value) => String(value || '').split('\n').map(line => {
  const text = line.trim()
  if (!text || text !== text.toLocaleUpperCase('es')) return text
  const lower = text.toLocaleLowerCase('es')
  return lower.charAt(0).toLocaleUpperCase('es') + lower.slice(1)
}).join('\n')

export const productName = (product) => {
  const firstLine = String(product?.descripcion || '').split('\n')[0].trim()
  // Only use a short description that explicitly names the piece. Do not
  // turn dimensions, sales conditions, or a guessed material into a title.
  if (/^(abanico|velo|mantilla|tocado|diadema|corona|pendientes|peina|peineta|liga|gemelos|cintur[oó]n|can can|arras)\b/i.test(firstLine)
      && firstLine.length <= 95 && !/\d|%|€/.test(firstLine)) {
    return sentenceCase(firstLine.replace(/\.$/, '')).replace(/^Abanico novia\b/, 'Abanico de novia')
  }
  return productReference(product)
}

export const productWhatsAppUrl = (product) => {
  const message = `Hola Ibernovia, quisiera consultar sobre ${productName(product)}. Referencia: ${productReference(product)}. https://www.ibernovia.es/producto/${encodeURIComponent(product.id)}`
  return `https://wa.me/34619625679?text=${encodeURIComponent(message)}`
}
