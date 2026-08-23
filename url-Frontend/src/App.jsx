import { useState } from 'react'
import { buildShortLink, createShortUrl } from './api.js'

function App() {
  const [longUrl, setLongUrl] = useState('')
  const [shortLink, setShortLink] = useState('')
  const [status, setStatus] = useState('idle') // idle | loading | success | error
  const [error, setError] = useState('')
  const [copied, setCopied] = useState(false)

  async function handleSubmit(event) {
    event.preventDefault()
    setError('')
    setCopied(false)
    setShortLink('')
    setStatus('loading')

    try {
      const data = await createShortUrl(longUrl.trim())
      setShortLink(buildShortLink(data.shortCode))
      setStatus('success')
    } catch (err) {
      setStatus('error')
      setError(err.message || 'Something went wrong')
    }
  }

  async function handleCopy() {
    if (!shortLink) return
    await navigator.clipboard.writeText(shortLink)
    setCopied(true)
  }

  return (
    <div className="page">
      <div className="atmosphere" aria-hidden="true">
        <div className="orb orb-a" />
        <div className="orb orb-b" />
        <div className="grid-wash" />
        <svg className="path-art" viewBox="0 0 1200 800" preserveAspectRatio="xMidYMid slice">
          <path
            className="route route-1"
            d="M-40 620 C 220 520, 360 740, 560 560 S 920 420, 1280 480"
          />
          <path
            className="route route-2"
            d="M-20 180 C 260 260, 420 40, 680 160 S 980 280, 1240 120"
          />
          <circle className="node node-1" cx="560" cy="560" r="7" />
          <circle className="node node-2" cx="680" cy="160" r="7" />
        </svg>
      </div>

      <main className="hero">
        <p className="brand">Shortner</p>
        <h1>Long links, cut clean.</h1>
        <p className="lede">
          Paste a URL. Get a short code backed by your Spring API.
        </p>

        <form className="shorten-form" onSubmit={handleSubmit}>
          <label className="sr-only" htmlFor="longUrl">
            Long URL
          </label>
          <input
            id="longUrl"
            type="url"
            name="longUrl"
            required
            placeholder="https://example.com/very/long/path"
            value={longUrl}
            onChange={(e) => setLongUrl(e.target.value)}
            disabled={status === 'loading'}
          />
          <button type="submit" disabled={status === 'loading' || !longUrl.trim()}>
            {status === 'loading' ? 'Shortening…' : 'Shorten'}
          </button>
        </form>

        {status === 'error' && <p className="message error">{error}</p>}

        {status === 'success' && shortLink && (
          <div className="result" role="status">
            <a href={shortLink} target="_blank" rel="noreferrer">
              {shortLink}
            </a>
            <button type="button" className="copy-btn" onClick={handleCopy}>
              {copied ? 'Copied' : 'Copy'}
            </button>
          </div>
        )}
      </main>
    </div>
  )
}

export default App
