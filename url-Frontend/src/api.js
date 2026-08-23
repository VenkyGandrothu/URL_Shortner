const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080'

export async function createShortUrl(longUrl) {
  const response = await fetch(`${API_BASE}/api/v1/urls`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ longUrl }),
  })

  if (!response.ok) {
    const text = await response.text()
    throw new Error(text || 'Failed to shorten URL')
  }

  return response.json()
}

export function buildShortLink(shortCode) {
  return `${API_BASE}/${shortCode}`
}
