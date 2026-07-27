import { useEffect, useState, useMemo } from 'react'
import { FiSearch } from 'react-icons/fi'
import { quantityService } from '../../services/quantityService.js'
import styles from './History.module.css'

const PAGE_SIZE = 8

export default function History() {
  const [records, setRecords] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [search, setSearch] = useState('')
  const [operationFilter, setOperationFilter] = useState('ALL')
  const [typeFilter, setTypeFilter] = useState('ALL')
  const [page, setPage] = useState(1)

  useEffect(() => {
    quantityService.getHistory()
      .then(setRecords)
      .catch(() => setError('Could not load history. Please try again later.'))
      .finally(() => setLoading(false))
  }, [])

  const filtered = useMemo(() => {
    return records.filter((r) => {
      const matchesOperation = operationFilter === 'ALL' || r.operation === operationFilter
      const matchesType = typeFilter === 'ALL' || r.firstQuantity?.measurementType === typeFilter
      const matchesSearch = search === '' || JSON.stringify(r).toLowerCase().includes(search.toLowerCase())
      return matchesOperation && matchesType && matchesSearch
    })
  }, [records, search, operationFilter, typeFilter])

  const totalPages = Math.max(1, Math.ceil(filtered.length / PAGE_SIZE))
  const paginated = filtered.slice((page - 1) * PAGE_SIZE, page * PAGE_SIZE)
  const operationTypes = ['ALL', 'COMPARE', 'CONVERT', 'ADD', 'SUBTRACT', 'DIVIDE']
  const measurementTypes = ['ALL', 'LENGTH', 'WEIGHT', 'VOLUME', 'TEMPERATURE']

  const formatResult = (record) => {
    if (record.error) return record.errorMessage || 'Error'
    const res = record.result
    if (res && typeof res === 'object' && 'value' in res) return `${Number(res.value).toFixed(2)} ${res.unit ?? ''}`
    return String(res)
  }

  return (
    <section className={`container ${styles.wrapper}`}>
      <h1 className={styles.heading}>Operation History</h1>
      <p className={styles.subheading}>A full record of every measurement operation you've run.</p>

      <div className={styles.toolbar}>
        <div className={styles.searchBox}>
          <FiSearch />
          <input placeholder="Search history..." value={search}
            onChange={(e) => { setSearch(e.target.value); setPage(1) }} />
        </div>
        <select value={operationFilter} onChange={(e) => { setOperationFilter(e.target.value); setPage(1) }}>
          {operationTypes.map((op) => <option key={op} value={op}>{op === 'ALL' ? 'All Operations' : op}</option>)}
        </select>
        <select value={typeFilter} onChange={(e) => { setTypeFilter(e.target.value); setPage(1) }}>
          {measurementTypes.map((t) => <option key={t} value={t}>{t === 'ALL' ? 'All Types' : t}</option>)}
        </select>
      </div>

      <div className={styles.tableCard}>
        {loading && <p className={styles.info}>Loading history...</p>}
        {error && <p className={styles.errorMsg}>{error}</p>}

        {!loading && !error && (
          <>
            <div className={styles.tableScroll}>
              <table className={styles.table}>
                <thead>
                  <tr>
                    <th>#</th><th>Type</th><th>Operation</th><th>First Value</th><th>Second Value</th><th>Result</th><th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {paginated.map((r) => (
                    <tr key={r.id}>
                      <td>{r.id}</td>
                      <td>{r.firstQuantity?.measurementType}</td>
                      <td><span className={styles.opBadge}>{r.operation}</span></td>
                      <td>{r.firstQuantity?.value} {r.firstQuantity?.unit}</td>
                      <td>{r.secondQuantity?.value} {r.secondQuantity?.unit}</td>
                      <td>{formatResult(r)}</td>
                      <td><span className={`${styles.statusBadge} ${r.error ? styles.statusError : styles.statusOk}`}>{r.error ? 'Failed' : 'Success'}</span></td>
                    </tr>
                  ))}
                  {paginated.length === 0 && <tr><td colSpan={7} className={styles.emptyRow}>No records found.</td></tr>}
                </tbody>
              </table>
            </div>

            <div className={styles.pagination}>
              <button disabled={page === 1} onClick={() => setPage((p) => p - 1)}>Prev</button>
              <span>Page {page} of {totalPages}</span>
              <button disabled={page === totalPages} onClick={() => setPage((p) => p + 1)}>Next</button>
            </div>
          </>
        )}
      </div>
    </section>
  )
}