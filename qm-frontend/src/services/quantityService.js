import axiosInstance from './axiosInstance'

const buildPayload = (thisQuantity, thatQuantity, targetUnit) => ({
  thisQuantity,
  thatQuantity,
  targetUnit: targetUnit || thisQuantity.unit
})

export const quantityService = {
  compare: (thisQuantity, thatQuantity) =>
    axiosInstance.post('/quantity/compare', buildPayload(thisQuantity, thatQuantity, thisQuantity.unit)).then(r => r.data),

  convert: (thisQuantity, targetUnit) =>
    axiosInstance.post('/quantity/convert', buildPayload(thisQuantity, thisQuantity, targetUnit)).then(r => r.data),

  add: (thisQuantity, thatQuantity, targetUnit) =>
    axiosInstance.post('/quantity/add', buildPayload(thisQuantity, thatQuantity, targetUnit)).then(r => r.data),

  subtract: (thisQuantity, thatQuantity, targetUnit) =>
    axiosInstance.post('/quantity/subtract', buildPayload(thisQuantity, thatQuantity, targetUnit)).then(r => r.data),

  divide: (thisQuantity, thatQuantity) =>
    axiosInstance.post('/quantity/divide', buildPayload(thisQuantity, thatQuantity, thisQuantity.unit)).then(r => r.data),

  getHistory: () => axiosInstance.get('/quantity/history').then(r => r.data),
  getHistoryByOperation: (operation) => axiosInstance.get(`/quantity/history/${operation}`).then(r => r.data),
  getHistoryByMeasurementType: (type) => axiosInstance.get(`/quantity/measurement/${type}`).then(r => r.data),
  getErroredOperations: () => axiosInstance.get('/quantity/errors').then(r => r.data),
  getOperationCount: (operation) => axiosInstance.get(`/quantity/count/${operation}`).then(r => r.data)
}
