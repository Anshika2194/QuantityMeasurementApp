import { useState, useEffect } from 'react'
import { FiRefreshCw } from 'react-icons/fi'
import { quantityService } from '../../services/quantityService.js'
import { MEASUREMENT_TYPES, UNITS_BY_TYPE, ALLOWED_OPERATIONS } from '../../utils/constants.js'
import styles from './MeasurementModule.module.css'

const OPERATION_LABELS = { COMPARE: 'Compare', CONVERT: 'Convert', ADD: 'Add', SUBTRACT: 'Subtract', DIVIDE: 'Divide' }

export default function MeasurementModule() {
  const [measurementType, setMeasurementType] = useState(MEASUREMENT_TYPES.LENGTH)
  const [operation, setOperation] = useState('COMPARE')
  const [firstValue, setFirstValue] = useState('')
  const [firstUnit, setFirstUnit] = useState(UNITS_BY_TYPE.LENGTH[0])
  const [secondValue, setSecondValue] = useState('')
  const [secondUnit, setSecondUnit] = useState(UNITS_BY_TYPE.LENGTH[0])
  const [targetUnit, setTargetUnit] = useState(UNITS_BY_TYPE.LENGTH[0])
  const [resultUnit, setResultUnit] = useState('')
  const [result, setResult] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const units = UNITS_BY_TYPE[measurementType]
  const allowedOps = ALLOWED_OPERATIONS[measurementType]

  useEffect(() => {
    setFirstUnit(units[0])
    setSecondUnit(units[0])
    setTargetUnit(units[0])
    setResult(null)
    setError('')
    if (!allowedOps.includes(operation)) setOperation(allowedOps[0])
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [measurementType])

  useEffect(() => { setResult(null); setError('') }, [operation])

  const buildQuantity = (value, unit) => ({ value: parseFloat(value), unit, measurementType })

  const handleSubmit = async (event) => {
    event.preventDefault()
    setError(''); setResult(null)

    if (firstValue === '' || (operation !== 'CONVERT' && secondValue === '')) {
      setError('Please fill in all required values.')
      return
    }

    const thisQuantity = buildQuantity(firstValue, firstUnit)
    const thatQuantity = buildQuantity(
      operation === 'CONVERT' ? firstValue : secondValue,
      operation === 'CONVERT' ? firstUnit : secondUnit
    )

    setLoading(true)
    try {
      let response
      switch (operation) {
        case 'COMPARE':
          response = await quantityService.compare(thisQuantity, thatQuantity)
          setResult({ type: 'boolean', value: response })
          break
        case 'CONVERT':
          response = await quantityService.convert(thisQuantity, targetUnit)
          setResult({ type: 'quantity', value: response })
          setResultUnit(response.unit)
          break
        case 'ADD':
          response = await quantityService.add(thisQuantity, thatQuantity, firstUnit)
          setResult({ type: 'quantity', value: response })
          setResultUnit(response.unit)
          break
        case 'SUBTRACT':
          response = await quantityService.subtract(thisQuantity, thatQuantity, firstUnit)
          setResult({ type: 'quantity', value: response })
          setResultUnit(response.unit)
          break
        case 'DIVIDE':
          response = await quantityService.divide(thisQuantity, thatQuantity)
          setResult({ type: 'number', value: response })
          break
        default: break
      }
    } catch (err) {
      setError(err?.response?.data?.message || 'Something went wrong. Please check your inputs.')
    } finally {
      setLoading(false)
    }
  }

  const handleResultUnitChange = async (event) => {
    const newUnit = event.target.value
    setResultUnit(newUnit)
    if (!result || result.type !== 'quantity') return

    setLoading(true); setError('')
    try {
      const converted = await quantityService.convert(
        { value: result.value.value, unit: result.value.unit, measurementType },
        newUnit
      )
      setResult({ type: 'quantity', value: converted })
    } catch {
      setError('Could not convert result to the selected unit.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className={styles.card}>
      <h3 className={styles.title}>Quantity Measurement</h3>

      <div className={styles.typeTabs}>
        {Object.values(MEASUREMENT_TYPES).map((type) => (
          <button key={type} type="button"
            className={`${styles.tab} ${measurementType === type ? styles.tabActive : ''}`}
            onClick={() => setMeasurementType(type)}>
            {type.charAt(0) + type.slice(1).toLowerCase()}
          </button>
        ))}
      </div>

      <div className={styles.opTabs}>
        {allowedOps.map((op) => (
          <button key={op} type="button"
            className={`${styles.opTab} ${operation === op ? styles.opTabActive : ''}`}
            onClick={() => setOperation(op)}>
            {OPERATION_LABELS[op]}
          </button>
        ))}
      </div>

      <form onSubmit={handleSubmit} className={styles.form}>
        <div className={styles.fieldRow}>
          <label className={styles.field}>
            <span>First Quantity</span>
            <div className={styles.inputGroup}>
              <input type="number" step="any" value={firstValue}
                onChange={(e) => setFirstValue(e.target.value)} placeholder="e.g. 10" required />
              <select value={firstUnit} onChange={(e) => setFirstUnit(e.target.value)}>
                {units.map((u) => <option key={u} value={u}>{u}</option>)}
              </select>
            </div>
          </label>

          {operation !== 'CONVERT' && (
            <label className={styles.field}>
              <span>Second Quantity</span>
              <div className={styles.inputGroup}>
                <input type="number" step="any" value={secondValue}
                  onChange={(e) => setSecondValue(e.target.value)} placeholder="e.g. 5" required />
                <select value={secondUnit} onChange={(e) => setSecondUnit(e.target.value)}>
                  {units.map((u) => <option key={u} value={u}>{u}</option>)}
                </select>
              </div>
            </label>
          )}

          {operation === 'CONVERT' && (
            <label className={styles.field}>
              <span>Target Unit</span>
              <select value={targetUnit} onChange={(e) => setTargetUnit(e.target.value)}>
                {units.map((u) => <option key={u} value={u}>{u}</option>)}
              </select>
            </label>
          )}
        </div>

        <button type="submit" className={styles.submitBtn} disabled={loading}>
          {loading ? <FiRefreshCw className={styles.spin} /> : OPERATION_LABELS[operation]}
        </button>
      </form>

      {error && <p className={styles.error}>{error}</p>}

      {result && (
        <div className={styles.resultBox}>
          {result.type === 'boolean' && (
            <span className={`${styles.badge} ${result.value ? styles.badgeSuccess : styles.badgeDanger}`}>
              {result.value ? 'Quantities Are Equal' : 'Quantities Are Not Equal'}
            </span>
          )}
          {result.type === 'number' && <p className={styles.resultValue}>{result.value}</p>}
          {result.type === 'quantity' && (
            <div className={styles.resultQuantity}>
              <p className={styles.resultValue}>{result.value.value.toFixed(4)}</p>
              {(operation === 'ADD' || operation === 'SUBTRACT') ? (
                <select value={resultUnit} onChange={handleResultUnitChange}>
                  {units.map((u) => <option key={u} value={u}>{u}</option>)}
                </select>
              ) : (
                <span className={styles.unitTag}>{result.value.unit}</span>
              )}
            </div>
          )}
        </div>
      )}
    </div>
  )
}
